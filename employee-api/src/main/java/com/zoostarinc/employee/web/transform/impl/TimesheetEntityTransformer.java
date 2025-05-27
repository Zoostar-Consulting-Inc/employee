package com.zoostarinc.employee.web.transform.impl;

import org.springframework.context.ApplicationContext;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.service.impl.AbstractTimesheetState;
import com.zoostarinc.employee.web.response.TimesheetResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.zoostar.common.core.Transformer;

@Getter
@RequiredArgsConstructor
public class TimesheetEntityTransformer implements Transformer<TimesheetResponse> {

	private final TimesheetEntity entity;

	private final ApplicationContext applicationContext;

	private final Class<AbstractTimesheetState> clazz;

	@Override
	public TimesheetResponse transform() {
		var response = new TimesheetResponse();
		response.setEmployee(entity.getEmployee());
		response.setWeekEnding(entity.getWeekEnding().toString());
		response.setWeekHours(entity.getHours());
		response.setState(new StateResponseTransformer(entity.getState(), applicationContext, clazz).transform());
		response.setUpdatedAt(entity.getUpdatedAt().toString());
		return response;
	}

}
