package com.zoostarinc.workflow.service.impl;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zoostarinc.employee.transformer.impl.TimesheetEntityTransformer;
import com.zoostarinc.employee.transformer.impl.TimesheetTransformer;
import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;
import com.zoostarinc.timesheet.model.Timesheet;
import com.zoostarinc.timesheet.service.TimesheetCrudService;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.transform.Transformer;
import net.zoostar.common.workflow.WorkflowService;

@Slf4j
@Service
@ToString
@RequiredArgsConstructor
public class TimesheetWorkflowService implements WorkflowService<Timesheet> {

	protected final TimesheetCrudService timesheetCrudManager;

	@Override
	@Transactional
	public Timesheet process(String doAction, Transformer<Timesheet> transformer) {
		TimesheetEntity entity = null;
		var timesheet = transformer.transform();
		var state = timesheet.getState();
		var actions = state.getActions();

		try {
			entity = timesheetCrudManager.retrieveByEmailAndWeekEnding(timesheet.getEmployee().getEmail(),
					timesheet.getWeekEnding());
			log.info("Updating existing timesheet: {}", entity);
			if (doAction == null) {
				timesheet.setTotalHours(entity.getHours());
			} else {
				for (var action : actions) {
					if (action.getName().equals(doAction)) {
						action.execute(timesheet);
						update(timesheet, entity);
						break;
					}
				}
			}
		} catch (NoSuchElementException e) {
			log.info("Creating new timesheet: {}", timesheet);
			entity = timesheetCrudManager.create(new TimesheetTransformer(timesheet));
		}

		return new TimesheetEntityTransformer(timesheet.getEmployee(), entity, this).transform();
	}

	private void update(Timesheet timesheet, TimesheetEntity entity) {
		entity.setHours(timesheet.getTotalHours());
		entity.setState(timesheet.getState().getName());
	}

}
