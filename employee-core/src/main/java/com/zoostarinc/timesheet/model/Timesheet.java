package com.zoostarinc.timesheet.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.zoostarinc.employee.model.Employee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.zoostar.common.core.workflow.State;
import net.zoostar.common.core.workflow.Workflowable;

@Getter
@Setter
@Builder
@ToString
public class Timesheet implements Workflowable {
	
	private String action;

	private Employee employee;
	
	private State<Timesheet> state;

	private LocalDate weekEnding;

	private int weekHours;

	private OffsetDateTime updatedAt;
	
}
