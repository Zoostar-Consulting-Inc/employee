package com.zoostarinc.employee.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.zoostarinc.employee.dao.repository.EmployeeRepository;
import com.zoostarinc.employee.service.TimesheetWorkflowService;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.workflow.timesheet.state.StateNew;

@Getter
@Service
@AllArgsConstructor
public class DefaultTimesheetWorkflowService implements TimesheetWorkflowService {
	
	public static final int DEFAULT_WEEKLY_HOURS = 40;

	private final EmployeeRepository employeeRepository;

	@Override
	public Timesheet newTimesheet(String email) {
		var value = employeeRepository.findByEmail(email);
		if (value.isEmpty()) {
			throw new EmptyResultDataAccessException("No results found for given employee!", 1);
		}

		var state = new StateNew();
		var timesheet = new Timesheet();
		timesheet.setEmployee(value.get());
		timesheet.setState(state);
		timesheet.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));
		timesheet.setHours(DEFAULT_WEEKLY_HOURS);
		return timesheet;
	}

}
