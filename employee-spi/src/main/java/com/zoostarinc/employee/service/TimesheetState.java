package com.zoostarinc.employee.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimesheetState {
	
	DRAFT("timesheetStateDraft");
	
	private final String beanName;
	
}
