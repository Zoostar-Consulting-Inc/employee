package net.zoostar.common.core.workflow.timesheet.state;

import java.util.Collections;
import java.util.Map;

import com.zoostarinc.timesheet.model.Timesheet;

import lombok.NoArgsConstructor;
import net.zoostar.common.core.workflow.Action;
import net.zoostar.common.core.workflow.State;

@NoArgsConstructor
public class StateNew implements State<Timesheet> {

	public static final String NAME = "New";
	
	@Override
	public Map<String, Action<Timesheet>> getActions() {
		return Collections.emptyMap();
	}

}
