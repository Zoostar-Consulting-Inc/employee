package com.zoostarinc.employee.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TimesheetRequest {

	private String state;
	
	private String action;
	
	private LocalDate weekEnding;
	
	private Integer hours;
	
}
