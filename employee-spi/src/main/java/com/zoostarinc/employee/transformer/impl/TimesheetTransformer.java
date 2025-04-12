package com.zoostarinc.employee.transformer.impl;

import java.util.UUID;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.AllArgsConstructor;
import net.zoostar.common.core.Transformer;

@AllArgsConstructor
public class TimesheetTransformer implements Transformer<TimesheetEntity> {

	private final Timesheet timesheet;
	
	@Override
	public TimesheetEntity transform() {
		var entity = new TimesheetEntity(UUID.randomUUID());
		entity.setEmployee(timesheet.getEmployee());
		entity.setHours(timesheet.getHours());
		entity.setState(timesheet.getState());
		entity.setWeekEnding(timesheet.getWeekEnding());
		return entity;
	}

}
