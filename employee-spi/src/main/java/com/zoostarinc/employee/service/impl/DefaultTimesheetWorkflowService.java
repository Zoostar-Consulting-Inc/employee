package com.zoostarinc.employee.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.dao.entity.TimesheetState;
import com.zoostarinc.employee.dao.repository.TimesheetRepository;
import com.zoostarinc.employee.request.TimesheetRequest;
import com.zoostarinc.employee.service.EmployeeService;
import com.zoostarinc.employee.service.TimesheetService;
import com.zoostarinc.employee.service.TimesheetWorkflowService;
import com.zoostarinc.employee.transformer.impl.TimesheetTransformer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Service
@AllArgsConstructor
public class DefaultTimesheetWorkflowService implements TimesheetWorkflowService {

	public static final int DEFAULT_WEEKLY_HOURS = 40;

	private final EmployeeService employeeManager;

	private final TimesheetRepository timesheetRepository;

	private final TimesheetService timesheetManager;

	@Override
	public TimesheetEntity newTimesheet(String email) {
		var timesheet = new TimesheetEntity();
		timesheet.setEmployee(employeeManager.retrieveByEmail(email));
		timesheet.setState(TimesheetState.NEW);
		timesheet.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));
		timesheet.setHours(DEFAULT_WEEKLY_HOURS);
		timesheet.setUpdateOn(LocalDateTime.now());
		return timesheet;
	}

	@Override
	public TimesheetEntity process(String email, TimesheetRequest request) {
		TimesheetEntity timesheetEntity = null;
		var employee = employeeManager.retrieveByEmail(email);

		try {
			timesheetEntity = timesheetManager.retrieveByEmployeeAndWeekEnding(employee, request.getWeekEnding());
			timesheetEntity = update(request, timesheetEntity);
		} catch (EmptyResultDataAccessException e) {
			timesheetEntity = create(email, request, employee);
		}

		return timesheetEntity;
	}

	protected TimesheetEntity update(TimesheetRequest request, TimesheetEntity timesheet) {
		var state = timesheet.getState();
		if (!state.toString().equalsIgnoreCase(request.getState())) {
			throw new IllegalArgumentException("Timesheet state mismatch!");
		}

		var action = state.getActions().get(request.getAction());
		if (action == null) {
			throw new IllegalArgumentException("No action found for given state!");
		}

		action.execute(timesheet);
		return timesheetManager.update(new TimesheetTransformer(timesheet));
	}

	protected TimesheetEntity create(String email, TimesheetRequest request, EmployeeEntity employee) {
		var timesheet = newTimesheet(email);
		var state = timesheet.getState();
		if (!state.toString().equalsIgnoreCase(request.getState())) {
			throw new IllegalArgumentException("Timesheet state mismatch!");
		}

		var action = state.getActions().get(request.getAction());
		if (action == null) {
			throw new IllegalArgumentException("No action found for given state!");
		}

		timesheet.setHours(request.getHours());
		timesheet.setUpdateOn(LocalDateTime.now());
		action.execute(timesheet);
		return timesheetManager.create(new TimesheetTransformer(timesheet));
	}

}
