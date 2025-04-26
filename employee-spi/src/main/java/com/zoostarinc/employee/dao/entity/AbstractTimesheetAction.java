package com.zoostarinc.employee.dao.entity;

import com.zoostarinc.employee.service.TimesheetService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.workflow.Action;

@Getter
@AllArgsConstructor
public abstract class AbstractTimesheetAction implements Action<TimesheetEntity> {

	private final TimesheetService timesheetManager;
	
	protected TimesheetEntity preExecute(TimesheetEntity timesheet) {
		return timesheet;
	}

	public final void execute(TimesheetEntity timesheet) {
		timesheet = preExecute(timesheet);
		
		postExecute(timesheet);
	}

	protected void postExecute(TimesheetEntity timesheet) {
		// To be overridden if required
	}
	
}
