package com.zoostarinc.employee.transformer.impl;

import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.zoostar.common.transform.Transformer;

@Getter
@RequiredArgsConstructor
public class TimesheetTransformer implements Transformer<TimesheetEntity> {

	private final Timesheet timesheet;

	@Override
	public TimesheetEntity transform() {
		var entity = new TimesheetEntity();
		entity.setEmail(timesheet.getEmployee().getEmail());
		entity.setHours(timesheet.getTotalHours());
		entity.setState(timesheet.getState().getName());
		entity.setWeekEnding(timesheet.getWeekEnding());
		return entity;
	}
	
	
}
