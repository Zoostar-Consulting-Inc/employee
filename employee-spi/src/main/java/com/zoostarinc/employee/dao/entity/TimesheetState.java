package com.zoostarinc.employee.dao.entity;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zoostarinc.employee.utils.Messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.workflow.Action;
import net.zoostar.common.core.workflow.State;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TimesheetState implements State<TimesheetEntity> {

	NEW("NEW", Map.of(Messages.getString("TimesheetAction.SAVE"), TimesheetAction.SAVE, //$NON-NLS-2$
			Messages.getString("TimesheetAction.SUBMIT"), TimesheetAction.SUBMIT)), //$NON-NLS-1$

	CREATED("CREATED",
			Map.of(Messages.getString("TimesheetAction.SAVE"), TimesheetAction.SAVE, //$NON-NLS-1$
					Messages.getString("TimesheetAction.SUBMIT"), TimesheetAction.SUBMIT)), //$NON-NLS-1$

	SUBMITTED("SUBMITTED", Collections.emptyMap()), //$NON-NLS-1$

	APPROVED("APPROVED", Collections.emptyMap()), //$NON-NLS-1$

	REJECTED("REJECTED", Collections.emptyMap()); //$NON-NLS-1$

	private final String name;

	private final Map<String, Action<TimesheetEntity>> actions;

	@Override
	public String toString() {
		return getName();
	}

}
