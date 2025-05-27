package com.zoostarinc.employee.web.transform.impl;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

import com.zoostarinc.employee.service.TimesheetState;
import com.zoostarinc.employee.service.impl.AbstractTimesheetState;
import com.zoostarinc.employee.web.response.StateResponse;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.zoostar.common.core.Transformer;
import net.zoostar.common.core.workflow.State;

@Getter
@RequiredArgsConstructor
public class StateResponseTransformer implements Transformer<StateResponse> {

	private final String strState;

	private final ApplicationContext applicationContext;

	private final Class<AbstractTimesheetState> clazz;

	@Override
	public StateResponse transform() {
		var stateResponse = new StateResponse();
		stateResponse.setName(strState);

		State<Timesheet> state = applicationContext.getBean(TimesheetState.valueOf(strState).getBeanName(), clazz);
		Collection<String> actions = state.getActions().keySet();
		stateResponse.setActions(actions);

		return stateResponse;
	}

}
