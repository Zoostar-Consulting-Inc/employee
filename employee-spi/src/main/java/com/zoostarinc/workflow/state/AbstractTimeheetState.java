package com.zoostarinc.workflow.state;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zoostarinc.timesheet.model.Timesheet;

import lombok.Getter;
import net.zoostar.common.workflow.Action;
import net.zoostar.common.workflow.State;

@Getter
public abstract class AbstractTimeheetState implements State<Timesheet> {

	private final String name;

	private final Collection<Action<Timesheet>> actions;
	
	private static final Map<String, AbstractTimeheetState> TIMESHEET_STATES = new ConcurrentHashMap<>();

	protected AbstractTimeheetState(String name) {
		this.name = name;
		this.actions = initActions();
		TIMESHEET_STATES.put(name, this); //ConcurrentHashMap does not support null so no null-check needed here.
	}

	protected abstract Collection<Action<Timesheet>> initActions();
	
	public static AbstractTimeheetState getState(String name) {
		return TIMESHEET_STATES.get(name);
	}
}
