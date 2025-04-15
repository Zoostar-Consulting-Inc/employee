package com.zoostarinc.employee.web.transformer.impl;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.Transformer;

@Getter
@AllArgsConstructor
public class TimesheetResponseTransformer implements Transformer<TimesheetEntity> {

	private final TimesheetEntity timesheet;
	
	@Override
	public TimesheetEntity transform() {
		return timesheet;
	}

}
