package com.zoostarinc.employee.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.zoostarinc.employee.dao.repository.EmployeeRepository;
import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.employee.service.TimesheetWorkflowService;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.Transformer;
import net.zoostar.common.core.workflow.timesheet.state.StateNew;

@Getter
@Service
@AllArgsConstructor
public class DefaultTimesheetWorkflowService implements TimesheetWorkflowService {

	private final EmployeeRepository employeeRepository;

	@Override
	public Timesheet newTimesheet(Transformer<Employee> transformer) {
		var employee = transformer.transform();
		var value = employeeRepository.findByEmail(employee.getEmail());
		if (value.isEmpty()) {
			throw new EmptyResultDataAccessException("No results found for given employee!", 1);
		}
		
		var state = new StateNew();
		var timesheet = new Timesheet();
		timesheet.setEmployee(value.get());
		timesheet.setState(state);
		return timesheet;
	}

}
