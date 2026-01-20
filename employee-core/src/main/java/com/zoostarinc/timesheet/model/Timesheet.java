package com.zoostarinc.timesheet.model;

import java.time.LocalDate;

import com.zoostarinc.employee.model.Employee;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.zoostar.common.workflow.State;
import net.zoostar.common.workflow.Workflowable;

@Getter
@Setter
@ToString
public class Timesheet implements Workflowable {

	private Employee employee;
	
	private State<Timesheet> state;
	
	private LocalDate weekEnding;
	
	private int totalHours;
	
	private String action;

}
