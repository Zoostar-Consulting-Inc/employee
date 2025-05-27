package com.zoostarinc.employee.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.dao.repository.TimesheetRepository;
import com.zoostarinc.employee.service.TimesheetService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DefaultTimesheetService<T extends TimesheetEntity> implements TimesheetService<T> {

	private final TimesheetRepository timesheetRepository;

	@Override
	public Collection<TimesheetEntity> retrieveByEmployeeAndState(EmployeeEntity employee, String state) {
		return timesheetRepository.findByEmployeeAndState(employee, state);
	}

}
