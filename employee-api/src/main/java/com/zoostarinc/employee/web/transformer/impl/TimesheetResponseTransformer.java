package com.zoostarinc.employee.web.transformer.impl;

import com.zoostarinc.timesheet.model.Timesheet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.Transformer;

@Getter
@AllArgsConstructor
public class TimesheetResponseTransformer implements Transformer<Timesheet> {

	private final Timesheet timesheet;
	
	@Override
	public Timesheet transform() {
		return timesheet;
	}

}
