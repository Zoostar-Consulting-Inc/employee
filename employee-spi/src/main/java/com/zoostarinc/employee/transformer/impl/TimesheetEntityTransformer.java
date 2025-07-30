package com.zoostarinc.employee.transformer.impl;

import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;
import com.zoostarinc.timesheet.model.Timesheet;
import com.zoostarinc.workflow.service.impl.TimesheetWorkflowService;
import com.zoostarinc.workflow.state.AbstractTimeheetState;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.zoostar.common.transform.Transformer;

@Getter
@ToString
@RequiredArgsConstructor
public class TimesheetEntityTransformer implements Transformer<Timesheet> {

	private final Employee employee;
	
	private final TimesheetEntity timesheetEntity;
	
	private final TimesheetWorkflowService timesheetWorkflowManager;

	@Override
	public Timesheet transform() {
		var timesheet = new Timesheet();
		timesheet.setEmployee(employee);
		timesheet.setState(AbstractTimeheetState.getState(timesheetEntity.getState()));
		timesheet.setTotalHours(timesheetEntity.getHours());
		timesheet.setWeekEnding(timesheetEntity.getWeekEnding());
		return timesheet;
	}
	
}
