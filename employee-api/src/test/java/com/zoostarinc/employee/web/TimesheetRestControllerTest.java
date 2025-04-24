package com.zoostarinc.employee.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.zoostarinc.employee.config.AbstractTestHarness;
import com.zoostarinc.employee.dao.entity.TimesheetAction;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.dao.entity.TimesheetState;
import com.zoostarinc.employee.request.TimesheetRequest;
import com.zoostarinc.employee.service.impl.DefaultTimesheetWorkflowService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TimesheetRestControllerTest extends AbstractTestHarness {

	@Test
	void testCreate200() throws Exception {
		// given
		String url = "/timesheet";

		// mock
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(persistentEmployeeEntity));

		// when
		var response = endpoint.perform(get(url).with(oidcLogin().oidcUser(testOidcUser(employee)))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		var timesheet = om.readValue(response.getContentAsString(), TimesheetEntity.class);
		log.info("Timesheet: {}", timesheet);

		var employee = timesheet.getEmployee();
		assertThat(employee).isNotNull().isEqualTo(persistentEmployeeEntity)
				.hasSameHashCodeAs(persistableEmployeeEntity);
		var duplicate = employee;
		assertThat(duplicate).isEqualTo(employee);
		assertThat(timesheet.getHours()).isEqualTo(DefaultTimesheetWorkflowService.DEFAULT_WEEKLY_HOURS);
		assertThat(TimesheetState.NEW).isEqualTo(timesheet.getState()).hasSameHashCodeAs(timesheet.getState());

		var actions = timesheet.getState().getActions();
		assertThat(actions).hasSize(2);
		var action = actions.get(TimesheetAction.SAVE.toString());
		assertThat(action).isEqualTo(TimesheetAction.SAVE);
		action.execute(timesheet);
		assertThat(timesheet.getState()).isEqualTo(TimesheetState.CREATED);

		action = actions.get(TimesheetAction.SUBMIT.toString());
		assertThat(action).isEqualTo(TimesheetAction.SUBMIT);

		var weekEnding = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
		assertThat(timesheet.getWeekEnding()).isEqualTo(weekEnding);
	}

	@Test
	void testCreate200WithNullOptionalValues() throws Exception {
		var employee = employee(this.employee.getEmail(), "", null);

		// given
		String url = "/timesheet";

		// mock
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(persistentEmployeeEntity));

		// when
		var response = endpoint.perform(get(url).with(oidcLogin().oidcUser(testOidcUser(employee)))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		var timesheet = om.readValue(response.getContentAsString(), TimesheetEntity.class);
		log.info("Timesheet: {}", timesheet);

		assertThat(timesheet.getEmployee()).isNotNull();
	}

	@Test
	void testCreate400NoUserFound() throws Exception {
		// given
		String url = "/timesheet";

		// mock
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());

		// when
		var response = endpoint.perform(get(url).with(oidcLogin().oidcUser(testOidcUser(employee)))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testCreate400RequiredFieldEmpty() throws Exception {
		var employee = employee("", null, "");

		// given
		String url = "/timesheet";

		// when
		var response = endpoint.perform(get(url).with(oidcLogin().oidcUser(testOidcUser(employee)))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testProcess200() throws Exception {
		var weekEnding = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

		// given
		var url = "/timesheet/process";
		var request = new TimesheetRequest();
		request.setAction(TimesheetAction.SAVE.toString());
		request.setHours(DefaultTimesheetWorkflowService.DEFAULT_WEEKLY_HOURS);
		request.setState(TimesheetState.NEW.toString());
		request.setWeekEnding(weekEnding);

		// mock
		var timesheetEntity = new TimesheetEntity(UUID.randomUUID());
		timesheetEntity.setEmployee(persistentEmployeeEntity);
		timesheetEntity.setHours(request.getHours());
		timesheetEntity.setState(TimesheetState.NEW);
		timesheetEntity.setWeekEnding(request.getWeekEnding());
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(persistentEmployeeEntity));
		when(timesheetRepository.findByEmployeeAndWeekEnding(persistentEmployeeEntity, weekEnding))
				.thenReturn(Optional.empty());

		// when
		var response = endpoint
				.perform(post(url).with(oidcLogin().oidcUser(testOidcUser(employee))).accept(APPLICATION_JSON_VALUE)
						.contentType(APPLICATION_JSON_VALUE).content(om.writeValueAsString(request)))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testCreate200ProcessUpdateHours() throws Exception {
		// given
		var url = "/timesheet/process";
		var request = new TimesheetRequest();
		request.setAction(TimesheetAction.SAVE.toString());
		request.setHours(DefaultTimesheetWorkflowService.DEFAULT_WEEKLY_HOURS + 10);
		request.setState(TimesheetState.NEW.toString());
		request.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));

		// mock
		var timesheetEntity = new TimesheetEntity(UUID.randomUUID());
		timesheetEntity.setEmployee(persistentEmployeeEntity);
		timesheetEntity.setHours(request.getHours());
		timesheetEntity.setState(TimesheetState.NEW);
		timesheetEntity.setWeekEnding(request.getWeekEnding());
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(persistentEmployeeEntity));
		when(timesheetRepository.findByEmployeeAndWeekEnding(persistentEmployeeEntity, request.getWeekEnding()))
				.thenReturn(Optional.of(timesheetEntity));

		// when
		var response = endpoint
				.perform(post(url).with(oidcLogin().oidcUser(testOidcUser(employee))).accept(APPLICATION_JSON_VALUE)
						.contentType(APPLICATION_JSON_VALUE).content(om.writeValueAsString(request)))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testCreate400ProcessWithInvalidState() throws Exception {
		// given
		var url = "/timesheet/process";
		var request = new TimesheetRequest();
		request.setAction(TimesheetAction.SAVE.toString());
		request.setHours(DefaultTimesheetWorkflowService.DEFAULT_WEEKLY_HOURS + 10);
		request.setState("INVALID");
		request.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));

		// mock
		var timesheetEntity = new TimesheetEntity(UUID.randomUUID());
		timesheetEntity.setEmployee(persistentEmployeeEntity);
		timesheetEntity.setHours(request.getHours());
		timesheetEntity.setState(TimesheetState.NEW);
		timesheetEntity.setWeekEnding(request.getWeekEnding());
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(persistentEmployeeEntity));
		when(timesheetRepository.findByEmployeeAndWeekEnding(persistentEmployeeEntity, request.getWeekEnding()))
				.thenReturn(Optional.empty());

		// when
		var response = endpoint
				.perform(post(url).with(oidcLogin().oidcUser(testOidcUser(employee))).accept(APPLICATION_JSON_VALUE)
						.contentType(APPLICATION_JSON_VALUE).content(om.writeValueAsString(request)))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testCreate400ProcessWithInvalidAction() throws Exception {
		// given
		var url = "/timesheet/process";
		var request = new TimesheetRequest();
		request.setAction("INVALID");
		request.setHours(DefaultTimesheetWorkflowService.DEFAULT_WEEKLY_HOURS + 10);
		request.setState("NEW");
		request.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));

		// mock
		var timesheetEntity = new TimesheetEntity(UUID.randomUUID());
		timesheetEntity.setEmployee(persistentEmployeeEntity);
		timesheetEntity.setHours(request.getHours());
		timesheetEntity.setState(TimesheetState.NEW);
		timesheetEntity.setWeekEnding(request.getWeekEnding());
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(persistentEmployeeEntity));
		when(timesheetRepository.findByEmployeeAndWeekEnding(persistentEmployeeEntity, request.getWeekEnding()))
				.thenReturn(Optional.empty());

		// when
		var response = endpoint
				.perform(post(url).with(oidcLogin().oidcUser(testOidcUser(employee))).accept(APPLICATION_JSON_VALUE)
						.contentType(APPLICATION_JSON_VALUE).content(om.writeValueAsString(request)))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testUpdate400ProcessWithInvalidState() throws Exception {
		// given
		var url = "/timesheet/process";
		var request = new TimesheetRequest();
		request.setAction(TimesheetAction.SAVE.toString());
		request.setHours(DefaultTimesheetWorkflowService.DEFAULT_WEEKLY_HOURS + 10);
		request.setState("INVALID");
		request.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));

		// mock
		var timesheetEntity = new TimesheetEntity(UUID.randomUUID());
		timesheetEntity.setEmployee(persistentEmployeeEntity);
		timesheetEntity.setHours(request.getHours());
		timesheetEntity.setState(TimesheetState.NEW);
		timesheetEntity.setWeekEnding(request.getWeekEnding());
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(persistentEmployeeEntity));
		when(timesheetRepository.findByEmployeeAndWeekEnding(persistentEmployeeEntity, request.getWeekEnding()))
				.thenReturn(Optional.of(timesheetEntity));

		// when
		var response = endpoint
				.perform(post(url).with(oidcLogin().oidcUser(testOidcUser(employee))).accept(APPLICATION_JSON_VALUE)
						.contentType(APPLICATION_JSON_VALUE).content(om.writeValueAsString(request)))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testUpdate400ProcessWithInvalidAction() throws Exception {
		// given
		var url = "/timesheet/process";
		var request = new TimesheetRequest();
		request.setAction("INVALID");
		request.setHours(DefaultTimesheetWorkflowService.DEFAULT_WEEKLY_HOURS + 10);
		request.setState("NEW");
		request.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));

		// mock
		var timesheetEntity = new TimesheetEntity(UUID.randomUUID());
		timesheetEntity.setEmployee(persistentEmployeeEntity);
		timesheetEntity.setHours(request.getHours());
		timesheetEntity.setState(TimesheetState.NEW);
		timesheetEntity.setWeekEnding(request.getWeekEnding());
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(persistentEmployeeEntity));
		when(timesheetRepository.findByEmployeeAndWeekEnding(persistentEmployeeEntity, request.getWeekEnding()))
				.thenReturn(Optional.of(timesheetEntity));

		// when
		var response = endpoint
				.perform(post(url).with(oidcLogin().oidcUser(testOidcUser(employee))).accept(APPLICATION_JSON_VALUE)
						.contentType(APPLICATION_JSON_VALUE).content(om.writeValueAsString(request)))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testSubmitted200Process() throws Exception {
		// given
		var url = "/timesheet/process";
		var request = new TimesheetRequest();
		request.setAction(TimesheetAction.SUBMIT.toString());
		request.setHours(DefaultTimesheetWorkflowService.DEFAULT_WEEKLY_HOURS + 10);
		request.setState("CREATED");
		request.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));

		// mock
		var timesheetEntity = new TimesheetEntity(UUID.randomUUID());
		timesheetEntity.setEmployee(persistentEmployeeEntity);
		timesheetEntity.setHours(request.getHours());
		timesheetEntity.setState(TimesheetState.CREATED);
		timesheetEntity.setWeekEnding(request.getWeekEnding());
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(persistentEmployeeEntity));
		when(timesheetRepository.findByEmployeeAndWeekEnding(persistentEmployeeEntity, request.getWeekEnding()))
				.thenReturn(Optional.of(timesheetEntity));

		// when
		var response = endpoint
				.perform(post(url).with(oidcLogin().oidcUser(testOidcUser(employee))).accept(APPLICATION_JSON_VALUE)
						.contentType(APPLICATION_JSON_VALUE).content(om.writeValueAsString(request)))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testProcess400LessThan40Hours() throws Exception {
		// given
		var url = "/timesheet/process";
		var request = new TimesheetRequest();
		request.setAction(TimesheetAction.SAVE.toString());
		request.setHours(39);
		request.setState(TimesheetState.NEW.toString());
		request.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));

		// mock
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(persistentEmployeeEntity));

		// when
		var response = endpoint
				.perform(post(url).with(oidcLogin().oidcUser(testOidcUser(employee))).accept(APPLICATION_JSON_VALUE)
						.contentType(APPLICATION_JSON_VALUE).content(om.writeValueAsString(request)))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

}
