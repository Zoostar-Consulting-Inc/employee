package com.zoostarinc.employee.model;

import java.time.LocalDate;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.zoostar.common.core.Hierarchical;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Hierarchical<Employee> {

	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private LocalDate dateOfBirth;
	
	private Employee parent;
	
	private Collection<Employee> children;

	@Override
	public void addChild(Employee child) {
		if(child != null) {
			children.add(child);
		}
	}

}
