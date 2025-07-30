package com.zoostarinc.api.response;

import java.time.LocalDate;

import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.workflow.state.AbstractTimeheetState;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TimesheetResponse {

	private Employee employee;
	
	private AbstractTimeheetState state;
	
	private LocalDate weekEnding;
	
	private int hours;

}
