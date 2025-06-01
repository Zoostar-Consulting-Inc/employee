package com.zoostarinc.employee.service.impl;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.dao.repository.TimesheetRepository;
import com.zoostarinc.employee.service.TimesheetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultTimesheetService implements TimesheetService<TimesheetEntity> {

	private final TimesheetRepository timesheetRepository;

	@Override
	public Collection<TimesheetEntity> retrieveByEmployeeAndState(EmployeeEntity employee, String state) {
		return timesheetRepository.findByEmployeeAndState(employee, state);
	}

	@Override
	public TimesheetEntity retrieveByEmployeeAndStateAndWeekEnding(EmployeeEntity employee, String name,
			LocalDate weekEnding) {
		var object = timesheetRepository.findByEmployeeAndStateAndWeekEnding(employee, name, weekEnding);
		if(object.isEmpty()) {
			throw new IllegalArgumentException(
					String.format("No timesheet found for employee email %s in state %s and week ending %s", 
							employee.getEmail(), name, weekEnding));
		}
		return object.get();
	}

	@Override
	@Transactional
	public TimesheetEntity update(TimesheetEntity timesheetEntity) {
		return timesheetRepository.save(timesheetEntity);
	}

}
