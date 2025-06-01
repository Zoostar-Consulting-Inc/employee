package com.zoostarinc.employee.service.impl;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.service.EmployeeService;
import com.zoostarinc.employee.service.TimesheetService;
import com.zoostarinc.employee.service.TimesheetState;
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
		log.info("Saving: {}...", timesheet);

		var state = timesheet.getState();
		if (!TimesheetState.DRAFT.name().equalsIgnoreCase(state.getName())) {
			throw new IllegalArgumentException(
					String.format("Timesheet must be in DRAFT state to be saved: %s", timesheet.toString()));
		}

		var timesheetEntity = getTimesheetManager().retrieveByEmployeeAndStateAndWeekEnding(
				getEmployeeManager().retrieveByEmail(timesheet.getEmployee().getEmail()), state.getName(),
				timesheet.getWeekEnding());
		timesheetEntity.setHours(timesheet.getWeekHours());
		timesheetEntity.setUpdatedAt(OffsetDateTime.now());
		timesheetEntity = getTimesheetManager().update(timesheetEntity);
		log.info("Saved timesheet: {}.", timesheetEntity);

		return timesheet;
	}

	@Override
	public String toString() {
		return getName();
	}

}
