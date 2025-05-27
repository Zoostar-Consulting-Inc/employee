package com.zoostarinc.employee.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zoostarinc.employee.config.AbstractTestHarness;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.service.TimesheetState;
import com.zoostarinc.employee.service.impl.TimesheetStateDraft;
import com.zoostarinc.employee.service.impl.TimesheetWorkflowService;
import com.zoostarinc.employee.web.response.TimesheetResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TimesheetRestControllerTest extends AbstractTestHarness {

	@Test
	void getDrafts200() throws Exception {

		// given
		String url = "/timesheet";

		// mock
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(persistentEmployeeEntity));
		when(timesheetRepository.findByEmployeeAndState(persistentEmployeeEntity, TimesheetStateDraft.NAME))
				.thenReturn(Collections.emptyList());

		// when
		var response = endpoint.perform(get(url).with(oidcLogin().oidcUser(testOidcUser(employee)))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		var results = om.readValue(response.getContentAsString(), new TypeReference<Collection<TimesheetResponse>>() {
		});
		assertThat(results).hasAtLeastOneElementOfType(TimesheetResponse.class);

		TimesheetResponse timesheetResponse = null;
		for (var result : results) {
			timesheetResponse = result;
		}
		assertThat(timesheetResponse.getEmployee()).isNotNull();
		assertThat(timesheetResponse.getState().getName()).isEqualTo(TimesheetState.DRAFT.name());
		assertThat(timesheetResponse.getWeekHours()).isEqualTo(TimesheetWorkflowService.DEFAULT_WEEKLY_HOURS);
		assertThat(OffsetDateTime.parse(timesheetResponse.getUpdatedAt())).isBeforeOrEqualTo(OffsetDateTime.now());
	}

	@Test
	void getDraftsWithMock200() throws Exception {

		// given
		String url = "/timesheet";

		// mock
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(persistentEmployeeEntity));
		when(timesheetRepository.findByEmployeeAndState(persistentEmployeeEntity, TimesheetStateDraft.NAME))
				.thenReturn(List.of(timesheetEntity(UUID.randomUUID())));

		// when
		var response = endpoint.perform(get(url).with(oidcLogin().oidcUser(testOidcUser(employee)))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		var results = om.readValue(response.getContentAsString(), new TypeReference<Collection<TimesheetResponse>>() {
		});
		assertThat(results).hasAtLeastOneElementOfType(TimesheetResponse.class);

		TimesheetResponse timesheetResponse = null;
		for (var result : results) {
			timesheetResponse = result;
		}
		assertThat(timesheetResponse.getEmployee()).isNotNull();
		assertThat(timesheetResponse.getState().getName()).isEqualTo(TimesheetState.DRAFT.name());
		assertThat(timesheetResponse.getWeekHours()).isEqualTo(TimesheetWorkflowService.DEFAULT_WEEKLY_HOURS);
		assertThat(OffsetDateTime.parse(timesheetResponse.getUpdatedAt())).isBeforeOrEqualTo(OffsetDateTime.now());
	}

	protected TimesheetEntity timesheetEntity(UUID id) {
		var entity = new TimesheetEntity(id);
		entity.setEmployee(persistentEmployeeEntity);
		entity.setHours(TimesheetWorkflowService.DEFAULT_WEEKLY_HOURS);
		entity.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));
		entity.setState(TimesheetState.DRAFT.name());
		entity.setUpdatedAt(OffsetDateTime.now());
		entity.setId(UUID.randomUUID());
		return entity;
	}

}
