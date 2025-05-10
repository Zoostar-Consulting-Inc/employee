package com.zoostarinc.employee.service.impl;

import org.springframework.stereotype.Service;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.Transformer;
import net.zoostar.common.core.workflow.WorkflowService;

@Getter
@Service
@AllArgsConstructor
public class DefaultTimesheetWorkflowService implements WorkflowService<TimesheetEntity> {

	public static final int DEFAULT_WEEKLY_HOURS = 40;

	@Override
	public TimesheetEntity process(Transformer<TimesheetEntity> transformer) {
		return transformer.transform();
	}

}
