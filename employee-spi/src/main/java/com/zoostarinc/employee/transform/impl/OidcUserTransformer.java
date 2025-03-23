package com.zoostarinc.employee.transform.impl;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import com.zoostarinc.employee.model.Employee;

import lombok.AllArgsConstructor;
import net.zoostar.common.core.Transformer;

@AllArgsConstructor
public class OidcUserTransformer implements Transformer<Employee> {

	private final OidcUser user;
	
	@Override
	public Employee transform() {
		var employee = new Employee();
		employee.setEmail(user.getEmail());
		employee.setFirstName(user.getGivenName());
		employee.setLastName(user.getFamilyName());
		employee.setUsername(user.getEmail());
		return employee;
	}

}
