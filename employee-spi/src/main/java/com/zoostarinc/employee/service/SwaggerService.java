package com.zoostarinc.employee.service;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.model.Employee;

import net.zoostar.common.transform.Transformer;

public interface SwaggerService {

	String getRedirectUrl(OidcUser user);

	EmployeeEntity createIfNotFound(Transformer<Employee> transformer);

}