package com.zoostarinc.employee.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.service.TimesheetService;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.core.Transformer;
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
//		var timesheet = transformer.transform();
//		
//		var action = timesheet.getAction();
//		if (StringUtils.hasText(action)) {
//			applicationContext
//					.getBean(TimesheetAction.valueOf(action.toUpperCase()).getName(), AbstractTimesheetAction.class)
//					.execute(timesheet);
//		} else {
//			try {
//				var timesheetEntity = timesheetManager.retrieveByEmailAndWeekEnding(timesheet.getEmployee().getEmail(), timesheet.getWeekEnding());
//				
//			} catch(EmptyResultDataAccessException e) {
//				log.info("Created new timesheet: {}", timesheet);
//			}
//		}
//
//		log.info("Processed timesheet: {}.", timesheet);
		//TODO
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
