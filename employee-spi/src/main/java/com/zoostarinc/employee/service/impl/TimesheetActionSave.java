package com.zoostarinc.employee.service.impl;

import org.springframework.stereotype.Service;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.service.EmployeeService;
import com.zoostarinc.employee.service.TimesheetService;
import com.zoostarinc.employee.utils.Messages;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TimesheetActionSave extends AbstractTimesheetAction {

	public static final String NAME = Messages.getString("TimesheetAction.SAVE");
	
	protected TimesheetActionSave(EmployeeService employeeManager, TimesheetService<TimesheetEntity> timesheetManager) {
		super(NAME, employeeManager, timesheetManager);
	}

	@Override
	protected Timesheet doExecute(Timesheet timesheet) {
		log.info("Saving: {}", timesheet);
		return null;
	}

	@Override
	public String toString() {
		return getName();
	}

}
