package com.zoostarinc.employee.dao.entity;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.workflow.Action;
import net.zoostar.common.core.workflow.State;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TimesheetState implements State<TimesheetEntity> {
	
	NEW("NEW", Map.of(
			"Save", TimesheetAction.SAVE,
			"Submit", TimesheetAction.SUBMIT)),
	
	CREATED("CREATED", Collections.emptyMap()),
	SUBMITTED("SUBMITTED", Collections.emptyMap());
	
	private final String name;
	
	private final Map<String, Action<TimesheetEntity>> actions;
	
	@Override
	public String toString() {
		return getName();
	}
	
}
