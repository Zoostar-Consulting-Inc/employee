package net.zoostar.common.core.workflow.timesheet.state;

import java.util.Collections;
import java.util.Map;

import com.zoostarinc.timesheet.model.Timesheet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.zoostar.common.core.workflow.Action;
import net.zoostar.common.core.workflow.State;
import net.zoostar.common.core.workflow.timesheet.action.TimesheetAction;

@Getter
@ToString
@AllArgsConstructor
public enum TimesheetState implements State<Timesheet> {
	
	NEW(Map.of("Save", TimesheetAction.SAVE)),
	CREATED(Collections.emptyMap());
	
	private final Map<String, Action<Timesheet>> actions;
	
}
