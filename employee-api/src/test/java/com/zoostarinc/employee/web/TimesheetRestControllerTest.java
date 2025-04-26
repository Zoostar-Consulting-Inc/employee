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
import com.zoostarinc.employee.dao.entity.TimesheetAction;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.dao.entity.TimesheetState;
import com.zoostarinc.employee.service.impl.DefaultTimesheetWorkflowService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TimesheetRestControllerTest extends AbstractTestHarness {

	@Test
	void testCreate200() throws Exception {
		// given
		String url = "/timesheet/process";

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

}
