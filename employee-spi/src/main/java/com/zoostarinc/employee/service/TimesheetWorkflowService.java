package com.zoostarinc.employee.service;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.request.TimesheetRequest;
import com.zoostarinc.timesheet.model.Timesheet;

public interface TimesheetWorkflowService {

	Timesheet newTimesheet(String email);

	TimesheetEntity process(String email, TimesheetRequest request);

}
