package com.zoostarinc.employee.service;

import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.timesheet.model.Timesheet;

import net.zoostar.common.core.Transformer;

public interface TimesheetWorkflowService {

	Timesheet newTimesheet(Transformer<Employee> transformer);

}
