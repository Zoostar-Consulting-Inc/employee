package net.zoostar.common.core.workflow.timesheet.action;

import com.zoostarinc.timesheet.model.Timesheet;

import net.zoostar.common.core.workflow.Action;
import net.zoostar.common.core.workflow.timesheet.state.TimesheetState;

public enum TimesheetAction implements Action<Timesheet> {

	SAVE {

		@Override
		public void execute(Timesheet timesheet) {
			timesheet.setState(TimesheetState.CREATED);
		}
		
	}
}
