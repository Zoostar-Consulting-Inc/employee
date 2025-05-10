package com.zoostarinc.employee.transformer.impl;

import java.time.OffsetDateTime;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.request.TimesheetRequest;
import com.zoostarinc.employee.service.impl.DefaultTimesheetWorkflowService;

import lombok.AllArgsConstructor;
import net.zoostar.common.core.Transformer;

@AllArgsConstructor
public class TimesheetTransformer implements Transformer<TimesheetEntity> {
	
	private final TimesheetRequest request;
	
	private final TimesheetEntity timesheet;

	@Override
	public TimesheetEntity transform() {
		var hours = request.getHours();
		if(hours < DefaultTimesheetWorkflowService.DEFAULT_WEEKLY_HOURS) {
			throw new IllegalArgumentException("Weekly hours may not be less than 40!");
		}
		timesheet.setHours(hours);
		timesheet.setUpdateOn(OffsetDateTime.now());
		return timesheet;
	}

}
