package com.zoostarinc.employee.service;

import java.util.Collection;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;

public interface TimesheetService<T extends TimesheetEntity> {

	Collection<TimesheetEntity> retrieveByEmployeeAndState(EmployeeEntity employee, String state);
	
}
