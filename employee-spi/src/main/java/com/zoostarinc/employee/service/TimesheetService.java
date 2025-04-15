package com.zoostarinc.employee.service;

import java.time.LocalDate;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;

import net.zoostar.common.core.Transformer;

public interface TimesheetService {

	TimesheetEntity create(Transformer<TimesheetEntity> transformer);
	
	TimesheetEntity retrieveByEmployeeAndWeekEnding(EmployeeEntity employee, LocalDate weekEnding);
	
}
