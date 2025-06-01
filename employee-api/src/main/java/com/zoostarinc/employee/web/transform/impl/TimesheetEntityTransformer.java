package com.zoostarinc.employee.web.transform.impl;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.web.response.TimesheetResponse;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.zoostar.common.core.Transformer;
import net.zoostar.common.core.workflow.WorkflowService;

@Getter
@RequiredArgsConstructor
public class TimesheetEntityTransformer implements Transformer<TimesheetResponse> {

	private final TimesheetEntity entity;
	
	private final WorkflowService<Timesheet> timesheetManager;

	@Override
	public TimesheetResponse transform() {
		var response = new TimesheetResponse();
		response.setEmployee(entity.getEmployee());
		response.setWeekEnding(entity.getWeekEnding().toString());
		response.setWeekHours(entity.getHours());
		response.setState(new StateResponseTransformer(entity.getState(), timesheetManager).transform());
		response.setUpdatedAt(entity.getUpdatedAt().toString());
		return response;
	}

}
