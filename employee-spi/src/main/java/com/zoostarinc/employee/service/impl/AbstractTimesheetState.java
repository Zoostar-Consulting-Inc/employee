package com.zoostarinc.employee.service.impl;

import java.util.Map;

import com.zoostarinc.timesheet.model.Timesheet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.workflow.Action;
import net.zoostar.common.core.workflow.State;

@Getter
@AllArgsConstructor
public abstract class AbstractTimesheetState implements State<Timesheet> {

	private String name;
	
	private Map<String, Action<Timesheet>> actions;
	
	@Override
	public String toString() {
		return getName();
	}
}
