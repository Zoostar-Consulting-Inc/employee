package com.zoostarinc.employee.web.transform.impl;

import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.employee.request.TimesheetRequest;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.zoostar.common.core.Transformer;
import net.zoostar.common.core.workflow.WorkflowService;

@Getter
@RequiredArgsConstructor
public class TimesheetRequestTransformer implements Transformer<Timesheet> {

	private final Employee employee;

	private final TimesheetRequest timesheetRequest;

	private final WorkflowService<Timesheet> timesheetWorkflowManager;

	@Override
	public Timesheet transform() {
		return Timesheet.builder().action(timesheetRequest.getAction()).employee(employee)
				.state(timesheetWorkflowManager.getState(timesheetRequest.getState()))
				.weekEnding(timesheetRequest.getWeekEnding()).weekHours(timesheetRequest.getHours()).build();
	}

}
