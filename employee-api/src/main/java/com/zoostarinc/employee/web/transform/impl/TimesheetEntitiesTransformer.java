package com.zoostarinc.employee.web.transform.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.service.impl.TimesheetWorkflowService;
import com.zoostarinc.employee.web.response.TimesheetResponse;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.core.Transformer;
import net.zoostar.common.core.workflow.WorkflowService;

@Slf4j
@Getter
@RequiredArgsConstructor
public class TimesheetEntitiesTransformer implements Transformer<Collection<TimesheetResponse>> {

	private final EmployeeEntity employeeEntity;
	
	private final Collection<TimesheetEntity> entities;
	
	private final WorkflowService<Timesheet> timesheetManager;

	@Override
	public Collection<TimesheetResponse> transform() {
		Collection<TimesheetResponse> response = new ArrayList<>();
		for(var entity : entities) {
			response.add(new TimesheetEntityTransformer(entity, timesheetManager).transform());
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
		response.setState(new StateResponseTransformer("DRAFT", timesheetManager).transform());
		response.setUpdatedAt(OffsetDateTime.now().toString());
		log.info("Created new timesheet: {}", response);
		return response;
	}

}
