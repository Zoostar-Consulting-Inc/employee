package com.zoostarinc.employee.service;

import java.time.LocalDate;
import java.util.Collection;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;

public interface TimesheetService<T extends TimesheetEntity> {

	Collection<T> retrieveByEmployeeAndState(EmployeeEntity employee, String state);

	T retrieveByEmployeeAndStateAndWeekEnding(EmployeeEntity employee, String name, LocalDate weekEnding);

	T update(T timesheetEntity);
	
}
