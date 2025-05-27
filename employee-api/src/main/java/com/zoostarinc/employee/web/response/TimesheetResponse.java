package com.zoostarinc.employee.web.response;

import com.zoostarinc.employee.model.Employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TimesheetResponse {

	private Employee employee;

	private String weekEnding;

	private int weekHours;

	private String updatedAt;

	private StateResponse state;

}
