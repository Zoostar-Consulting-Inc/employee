package com.zoostarinc.api.transformer.impl;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import com.zoostarinc.api.request.TimesheetRequest;
import com.zoostarinc.employee.transformer.impl.OidcUserTransformer;
import com.zoostarinc.timesheet.model.Timesheet;
import com.zoostarinc.workflow.state.AbstractTimeheetState;
import com.zoostarinc.workflow.state.TimesheetStateDraft;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.zoostar.common.transform.Transformer;

@Getter
@ToString
@RequiredArgsConstructor
public class TimesheetRequestTransformer implements Transformer<Timesheet> {

	private final DefaultOidcUser user;
	
	private final TimesheetRequest request;

	@Override
	public Timesheet transform() {
		var timesheet = new Timesheet();
		timesheet.setEmployee(new OidcUserTransformer(user).transform());
		timesheet.setState(AbstractTimeheetState.getState(TimesheetStateDraft.NAME));
		timesheet.setWeekEnding(request.getWeekEnding());
		timesheet.setTotalHours(request.getHours());
		return timesheet;
	}

}
