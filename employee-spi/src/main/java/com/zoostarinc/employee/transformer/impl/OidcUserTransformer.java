package com.zoostarinc.employee.transformer.impl;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.StringUtils;

import com.zoostarinc.employee.model.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoostar.common.core.Transformer;

@Getter
@AllArgsConstructor
public class OidcUserTransformer implements Transformer<Employee> {

	private final OidcUser user;
	
	@Override
	public Employee transform() {
		if(user == null || !StringUtils.hasText(user.getEmail())) {
			throw new NullPointerException("Required value for field email is missing!");
		}
		
		var employee = new Employee();
		employee.setEmail(user.getEmail().trim().toLowerCase());
		employee.setFirstName(user.getGivenName() != null ? user.getGivenName().trim() : "");
		employee.setLastName(user.getFamilyName() != null ? user.getFamilyName().trim() : "");
		return employee;
	}

}
