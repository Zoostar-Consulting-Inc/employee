package com.zoostarinc.employee.service.impl;

import java.time.LocalDate;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.dao.repository.TimesheetRepository;
import com.zoostarinc.employee.service.TimesheetService;

import lombok.AllArgsConstructor;
import net.zoostar.common.core.Transformer;

@Service
@AllArgsConstructor
public class DefaultTimesheetService implements TimesheetService {

	private final TimesheetRepository timesheetRepository;

	@Override
	@Transactional
	public TimesheetEntity create(Transformer<TimesheetEntity> transformer) {
		return timesheetRepository.save(transformer.transform());
	}

	@Override
	public TimesheetEntity retrieveByEmployeeAndWeekEnding(EmployeeEntity employee, LocalDate weekEnding) {
		var object = timesheetRepository.findByEmployeeAndWeekEnding(employee, weekEnding);
		if(object.isEmpty()) {
			throw new EmptyResultDataAccessException("No timesheet found for given employee and week ending!", 1);
		}
		return object.get();
	}

}
