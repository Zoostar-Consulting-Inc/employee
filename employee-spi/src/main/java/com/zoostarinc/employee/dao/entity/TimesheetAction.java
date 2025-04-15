package com.zoostarinc.employee.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.workflow.Action;

@Getter
@AllArgsConstructor
public enum TimesheetAction implements Action<TimesheetEntity> {

	SAVE("Save") {

		@Override
		public void execute(TimesheetEntity timesheet) {
			timesheet.setState(TimesheetState.CREATED);
		}

	},

	SUBMIT("Submit") {

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
