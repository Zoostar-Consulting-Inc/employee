package com.zoostarinc.employee.web.transform.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.service.impl.AbstractTimesheetState;
import com.zoostarinc.employee.service.impl.TimesheetWorkflowService;
import com.zoostarinc.employee.web.response.TimesheetResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.core.Transformer;

@Slf4j
@Getter
@RequiredArgsConstructor
public class TimesheetEntitiesTransformer implements Transformer<Collection<TimesheetResponse>> {

	private final EmployeeEntity employeeEntity;
	
	private final Collection<TimesheetEntity> entities;
	
	private final ApplicationContext applicationContext;

	private final Class<AbstractTimesheetState> clazz;

	@Override
	public Collection<TimesheetResponse> transform() {
		Collection<TimesheetResponse> response = new ArrayList<>();
		for(var entity : entities) {
			response.add(new TimesheetEntityTransformer(entity, applicationContext, clazz).transform());
		}
		
		if(CollectionUtils.isEmpty(response)) {
			log.info("No draft timesheets found for: {}", employeeEntity);
			response.add(timesheetResponse());
		}
		
		return response;
	}

	protected TimesheetResponse timesheetResponse() {
		var response = new TimesheetResponse();
		response.setEmployee(employeeEntity);
		response.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).toString());
		response.setWeekHours(TimesheetWorkflowService.DEFAULT_WEEKLY_HOURS);
		response.setState(new StateResponseTransformer("DRAFT", applicationContext, clazz).transform());
		response.setUpdatedAt(OffsetDateTime.now().toString());
		log.info("Created new timesheet: {}", response);
		return response;
	}

}
