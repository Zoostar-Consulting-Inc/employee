package com.zoostarinc.employee.service;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;

public interface EmployeeService {
	EmployeeEntity createIfNotFound(OidcUser user);
}
