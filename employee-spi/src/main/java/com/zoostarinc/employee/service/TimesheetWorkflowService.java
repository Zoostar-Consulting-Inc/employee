package com.zoostarinc.employee.service;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.request.TimesheetRequest;

public interface TimesheetWorkflowService {

	TimesheetEntity newTimesheet(String email);

	TimesheetEntity process(String email, TimesheetRequest request);

}
