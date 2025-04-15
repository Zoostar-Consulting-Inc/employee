package com.zoostarinc.employee.transformer.impl;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.service.impl.DefaultTimesheetWorkflowService;

import lombok.AllArgsConstructor;
import net.zoostar.common.core.Transformer;

@AllArgsConstructor
public class TimesheetTransformer implements Transformer<TimesheetEntity> {
	
	private final TimesheetEntity timesheet;

	@Override
	public TimesheetEntity transform() {
		if(timesheet.getHours() < DefaultTimesheetWorkflowService.DEFAULT_WEEKLY_HOURS) {
			throw new IllegalArgumentException("Weekly hours may not be less than 40!");
		}
		return timesheet;
	}

}
