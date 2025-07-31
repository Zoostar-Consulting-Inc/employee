package com.zoostarinc.api.transformer.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.timesheet.model.Timesheet;
import com.zoostarinc.workflow.state.AbstractTimeheetState;
import com.zoostarinc.workflow.state.TimesheetStateDraft;

import lombok.RequiredArgsConstructor;
import net.zoostar.common.transform.Transformer;

@RequiredArgsConstructor
public class DraftTimesheetTransformer implements Transformer<Timesheet> {

	private final DefaultOidcUser user;

	@Override
	public Timesheet transform() {
		var employee = new Employee();
		employee.setEmail(user.getEmail());
		employee.setFirstName(user.getGivenName());
		employee.setLastName(user.getFamilyName());

		var timesheet = new Timesheet();
		timesheet.setEmployee(employee);
		timesheet.setState(AbstractTimeheetState.getState(TimesheetStateDraft.NAME));
		timesheet.setWeekEnding(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));
		return timesheet;
	}

}
