package com.zoostarinc.employee.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.springframework.stereotype.Service;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.dao.repository.TimesheetRepository;
import com.zoostarinc.employee.request.TimesheetRequest;
import com.zoostarinc.employee.service.EmployeeService;
import com.zoostarinc.employee.service.TimesheetWorkflowService;
import com.zoostarinc.employee.transformer.impl.TimesheetTransformer;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.workflow.timesheet.state.TimesheetState;

@Getter
@Service
@AllArgsConstructor
public class DefaultTimesheetWorkflowService implements TimesheetWorkflowService {

	public static final int DEFAULT_WEEKLY_HOURS = 40;

	private final EmployeeService employeeManager;

	private final TimesheetRepository timesheetRepository;

	@Override
	public Timesheet newTimesheet(String email) {
		var timesheet = new Timesheet();
		timesheet.setEmployee(employeeManager.retrieveByEmail(email));
		timesheet.setState(TimesheetState.NEW);
		timesheet.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));
		timesheet.setHours(DEFAULT_WEEKLY_HOURS);
		return timesheet;
	}

	@Override
	public TimesheetEntity process(String email, TimesheetRequest request) {
		TimesheetEntity entity = null;
		var employee = employeeManager.retrieveByEmail(email);
		var object = timesheetRepository.findByEmployeeAndWeekEnding(employee, request.getWeekEnding());
		
		if(object.isEmpty()) {
			var timesheet = newTimesheet(email);
			var state = timesheet.getState();
			if(!state.toString().equals(request.getState())) {
				throw new IllegalArgumentException("Timesheet state mismatch!");
			}
			
			var action = state.getActions().get(request.getAction());
			if(action == null) {
				throw new IllegalArgumentException("No action found for given state!");
			}
			action.execute(timesheet);
			entity = timesheetRepository.save(new TimesheetTransformer(timesheet).transform());
		}
		
		return entity;
	}

}
