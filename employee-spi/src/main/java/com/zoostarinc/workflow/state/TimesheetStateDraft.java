package com.zoostarinc.workflow.state;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.zoostarinc.timesheet.model.Timesheet;
import com.zoostarinc.workflow.action.TimesheetActionSave;

import net.zoostar.common.workflow.Action;

@Service
public class TimesheetStateDraft extends AbstractTimeheetState {

	public static final String NAME = "DRAFT";
	
	public TimesheetStateDraft() {
		super(NAME);
	}
	
	@Override
	protected Collection<Action<Timesheet>> initActions() {
		Collection<Action<Timesheet>> actions = new HashSet<>();
		actions.add(new TimesheetActionSave());
		return actions;
	}

}
