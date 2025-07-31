package com.zoostarinc.workflow.action;

import com.zoostarinc.timesheet.model.Timesheet;
import com.zoostarinc.workflow.state.AbstractTimeheetState;
import com.zoostarinc.workflow.state.TimesheetStateDraft;

import net.zoostar.common.workflow.Action;

public class TimesheetActionSave implements Action<Timesheet> {

	public static final String NAME = "Save";
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void execute(Timesheet timesheet) {
		timesheet.setState(AbstractTimeheetState.getState(TimesheetStateDraft.NAME));
	}

}
