package com.zoostarinc.employee.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zoostarinc.timesheet.model.Timesheet;

import lombok.Getter;
import net.zoostar.common.core.workflow.Action;

@Getter
@Component
public class TimesheetStateDraft extends AbstractTimesheetState {

	public static final String NAME = "DRAFT";

	public TimesheetStateDraft(@Autowired Action<Timesheet> timesheetActionSave) {
		super(NAME, Map.of(TimesheetActionSave.NAME, timesheetActionSave));
	}

}
