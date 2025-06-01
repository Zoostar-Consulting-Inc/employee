package com.zoostarinc.employee.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.service.TimesheetAction;
import com.zoostarinc.employee.service.TimesheetService;
import com.zoostarinc.employee.service.TimesheetState;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.core.Transformer;
import net.zoostar.common.core.workflow.Action;
import net.zoostar.common.core.workflow.State;
import net.zoostar.common.core.workflow.WorkflowService;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class TimesheetWorkflowService implements WorkflowService<Timesheet>, ApplicationContextAware {

	public static final int DEFAULT_WEEKLY_HOURS = 40;

	private ApplicationContext applicationContext;

	private final TimesheetService<TimesheetEntity> timesheetManager;

	@Override
	@Transactional
	public Timesheet process(Transformer<Timesheet> transformer) {
		var timesheet = transformer.transform();

		var action = timesheet.getAction();
		if (StringUtils.hasText(action)) {
			getAction(action).execute(timesheet);
		} else {
			throw new IllegalArgumentException(
					String.format("Action must be specified for timesheet processing: %s", timesheet.toString()));
		}

		log.info("Processed timesheet: {}.", timesheet);
		return timesheet;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public State<Timesheet> getState(String state) {
		return applicationContext.getBean(TimesheetState.valueOf(state.toUpperCase()).getBeanName(),
				AbstractTimesheetState.class);
	}

	@Override
	public Action<Timesheet> getAction(String action) {
		return applicationContext.getBean(TimesheetAction.valueOf(action.toUpperCase()).getBeanName(),
				AbstractTimesheetAction.class);
	}

}
