package com.zoostarinc.employee.transformer.impl;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.StringUtils;

import com.zoostarinc.employee.model.Employee;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.zoostar.common.transform.Transformer;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class OidcUserTransformer implements Transformer<Employee> {

	private final OidcUser user;
	
	@Override
	public Employee transform() {
		if(user == null || !StringUtils.hasText(user.getEmail())) {
			throw new IllegalArgumentException("Required value for field email is missing!");
		}
		
		var employee = new Employee();
		employee.setEmail(user.getEmail().trim().toLowerCase());
		return employee;
	}

}