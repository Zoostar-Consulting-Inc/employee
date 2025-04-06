package net.zoostar.common.core.workflow.timesheet.state;

import java.util.Collections;
import java.util.Map;

import com.zoostarinc.timesheet.model.Timesheet;

import net.zoostar.common.core.workflow.Action;

public class StateNew extends AbstractTimesheetState {

	public static final String NAME = "New";

	public StateNew() {
		super(NAME);
	}
	
	@Override
	public Map<String, Action<Timesheet>> getActions() {
		return Collections.emptyMap();
	}

}
