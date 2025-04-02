package com.zoostarinc.timesheet.model;

import com.zoostarinc.employee.model.Employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.zoostar.common.core.workflow.State;
import net.zoostar.common.core.workflow.Workflowable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Timesheet implements Workflowable<Timesheet> {

	private Employee employee;

	private State<Timesheet> state;
	
}
