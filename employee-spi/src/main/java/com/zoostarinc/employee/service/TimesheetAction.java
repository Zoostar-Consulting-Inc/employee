package com.zoostarinc.employee.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimesheetAction {
	
	SAVE("timesheetActionSave");
	
	private final String beanName;
	
}
