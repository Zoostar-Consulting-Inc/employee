package com.zoostarinc.api.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TimesheetRequest {

	private String action;
	
	private LocalDate weekEnding;
	
	private int hours;
	
}
