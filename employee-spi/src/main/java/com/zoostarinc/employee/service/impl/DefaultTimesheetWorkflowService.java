package com.zoostarinc.employee.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
		return timesheet;
	}

	@Override
	public TimesheetEntity process(String email, TimesheetRequest request) {
		TimesheetEntity timesheetEntity = null;
		var employee = employeeManager.retrieveByEmail(email);

		try {
			timesheetEntity = timesheetManager.retrieveByEmployeeAndWeekEnding(employee, request.getWeekEnding());
		} catch (EmptyResultDataAccessException e) {
			timesheetEntity = save(email, request, employee);
		}

		return timesheetEntity;
	}

	protected TimesheetEntity save(String email, TimesheetRequest request, EmployeeEntity employee) {
		var timesheet = newTimesheet(email);
		var state = timesheet.getState();
		if (!state.toString().equalsIgnoreCase(request.getState())) {
			throw new IllegalArgumentException("Timesheet state mismatch!");
		}

		var action = state.getActions().get(request.getAction());
		if (action == null) {
			throw new IllegalArgumentException("No action found for given state!");
		}

		if (request.getHours() < DEFAULT_WEEKLY_HOURS) {
			throw new IllegalArgumentException("Weekly hours may not be less than 40!");
		}
		timesheet.setHours(request.getHours());

		action.execute(timesheet);
		return timesheetManager.create(new TimesheetTransformer(timesheet));
	}

}
