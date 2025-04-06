package com.zoostarinc.employee.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.zoostarinc.employee.config.AbstractTestHarness;
import com.zoostarinc.employee.service.impl.DefaultTimesheetWorkflowService;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.core.workflow.timesheet.state.AbstractTimesheetState;

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
		var timesheet = om.readValue(response.getContentAsString(), Timesheet.class);
		log.info("Timesheet: {}", timesheet);

		assertThat(timesheet.getEmployee()).isNotNull();
		assertThat(AbstractTimesheetState.STATE_NEW).isEqualTo(timesheet.getState())
				.hasSameHashCodeAs(timesheet.getState());
		assertThat(timesheet.getHours()).isEqualTo(DefaultTimesheetWorkflowService.DEFAULT_WEEKLY_HOURS);

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
		var timesheet = om.readValue(response.getContentAsString(), Timesheet.class);
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

}
