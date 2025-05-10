package com.zoostarinc.employee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	private String email;
	
	private String firstName;
	
	private String lastName;
	
}
