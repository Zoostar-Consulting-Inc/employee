package com.zoostarinc.employee.dao.entity;

import com.zoostarinc.employee.utils.Messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.workflow.Action;

@Getter
@AllArgsConstructor
public enum TimesheetAction implements Action<TimesheetEntity> {

	SAVE(Messages.getString("TimesheetAction.SAVE")) { //$NON-NLS-1$

		@Override
		public void execute(TimesheetEntity timesheet) {
			timesheet.setState(TimesheetState.CREATED);
		}

	},

	SUBMIT(Messages.getString("TimesheetAction.SUBMIT")) { //$NON-NLS-1$

		@Override
		public void execute(TimesheetEntity timesheet) {
			timesheet.setState(TimesheetState.SUBMITTED);
		}

	};

	private final String name;

	@Override
	public String toString() {
		return getName();
	}

}
