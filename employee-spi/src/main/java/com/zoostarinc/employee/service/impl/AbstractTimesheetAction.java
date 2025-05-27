package com.zoostarinc.employee.service.impl;

import java.time.format.DateTimeFormatter;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.service.EmployeeService;
import com.zoostarinc.employee.service.TimesheetService;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.zoostar.common.core.workflow.Action;

@Getter
@RequiredArgsConstructor
public abstract class AbstractTimesheetAction implements Action<Timesheet> {

	public static final DateTimeFormatter ISO_DATE_TIME_FORMAT_UPTO_SECONDS = DateTimeFormatter
			.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

	private final String name;
	
	private final EmployeeService employeeManager;

	private final TimesheetService<TimesheetEntity> timesheetManager;

	protected Timesheet preExecute(Timesheet timesheet) {
		return timesheet;
	}

	public final void execute(Timesheet timesheet) {
		postExecute(doExecute(preExecute(timesheet)));
	}

	protected abstract Timesheet doExecute(Timesheet timesheet);

	protected void postExecute(Timesheet timesheet) {
		// To be overridden if required
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
