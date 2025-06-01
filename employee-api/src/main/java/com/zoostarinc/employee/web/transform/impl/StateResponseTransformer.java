package com.zoostarinc.employee.web.transform.impl;

import java.util.Collection;

import com.zoostarinc.employee.web.response.StateResponse;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.zoostar.common.core.Transformer;
import net.zoostar.common.core.workflow.State;
import net.zoostar.common.core.workflow.WorkflowService;

@Getter
@RequiredArgsConstructor
public class StateResponseTransformer implements Transformer<StateResponse> {

	private final String strState;
	
	private final WorkflowService<Timesheet> timesheetWorkflowManager;

	@Override
	public StateResponse transform() {
		var stateResponse = new StateResponse();
		stateResponse.setName(strState);

		State<Timesheet> state = timesheetWorkflowManager.getState(strState);
		Collection<String> actions = state.getActions().keySet();
		stateResponse.setActions(actions);

		return stateResponse;
	}

}
