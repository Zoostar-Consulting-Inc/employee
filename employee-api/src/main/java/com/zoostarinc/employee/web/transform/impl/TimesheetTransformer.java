package com.zoostarinc.employee.web.transform.impl;

import com.zoostarinc.employee.web.response.TimesheetResponse;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.zoostar.common.core.Transformer;
import net.zoostar.common.core.workflow.WorkflowService;

@Getter
@RequiredArgsConstructor
public class TimesheetTransformer implements Transformer<TimesheetResponse> {

	private final Timesheet timesheet;

	private final WorkflowService<Timesheet> timesheetWorkflowManager;

	@Override
	public TimesheetResponse transform() {
		var response = new TimesheetResponse();
		response.setEmployee(timesheet.getEmployee());
		response.setState(
				new StateResponseTransformer(timesheet.getState().getName(), timesheetWorkflowManager).transform());
		response.setWeekEnding(timesheet.getWeekEnding().toString());
		response.setWeekHours(timesheet.getWeekHours());
		response.setUpdatedAt(timesheet.getUpdatedAt() != null ? timesheet.getUpdatedAt().toString() : null);
		return response;
	}

}
