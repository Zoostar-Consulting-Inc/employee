package com.zoostarinc.api.transformer.impl;

import com.zoostarinc.api.response.TimesheetResponse;
import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.timesheet.model.Timesheet;
import com.zoostarinc.workflow.state.AbstractTimeheetState;

import lombok.RequiredArgsConstructor;
import net.zoostar.common.transform.Transformer;

@RequiredArgsConstructor
public class TimesheetResponseTransformer implements Transformer<TimesheetResponse> {

	private final Timesheet timesheet;

	@Override
	public TimesheetResponse transform() {
		var employee = new Employee();
		employee.setEmail(timesheet.getEmployee().getEmail());
		employee.setFirstName(timesheet.getEmployee().getFirstName());
		employee.setLastName(timesheet.getEmployee().getLastName());
		
		var response = new TimesheetResponse();
		response.setEmployee(employee);
		response.setState(AbstractTimeheetState.getState(timesheet.getState().getName()));
		response.setHours(timesheet.getTotalHours());
		response.setWeekEnding(timesheet.getWeekEnding());
		return response;
	}

}
