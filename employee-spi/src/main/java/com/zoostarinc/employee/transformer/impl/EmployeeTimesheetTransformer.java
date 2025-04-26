package com.zoostarinc.employee.transformer.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAdjusters;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.dao.entity.TimesheetState;
import com.zoostarinc.employee.service.impl.DefaultTimesheetWorkflowService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.Transformer;

@Getter
@AllArgsConstructor
public class EmployeeTimesheetTransformer implements Transformer<TimesheetEntity> {

	public static final int DEFAULT_WEEKLY_HOURS = 40;

	private final EmployeeEntity employee;
	
	@Override
	public TimesheetEntity transform() {
		var timesheet = new TimesheetEntity();
		timesheet.setEmployee(employee);
		timesheet.setState(TimesheetState.NEW);
		timesheet.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));
		timesheet.setHours(DefaultTimesheetWorkflowService.DEFAULT_WEEKLY_HOURS);
		timesheet.setUpdateOn(OffsetDateTime.now());
		return timesheet;
	}

}
