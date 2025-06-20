package com.zoostarinc.employee.service.impl;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.service.EmployeeCrudService;
import com.zoostarinc.employee.service.EmployeeService;
import com.zoostarinc.employee.transformer.impl.OidcUserTransformer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultEmployeeService implements EmployeeService {

	protected final EmployeeCrudService employeeCrudService;
	
	@Override
	public EmployeeEntity createIfNotFound(OidcUser user) {
		EmployeeEntity entity = null;
		var email = user.getEmail();
		try {
			entity = employeeCrudService.retrieveByEmail(email);
			if(entity != null) {
				log.warn("Employee already exists with email: {}", email);
				throw new DuplicateKeyException("Employee with email " + email + " already exists!");
			}
		} catch (EmptyResultDataAccessException e) {
			log.trace("{}", e.getMessage());
			entity = employeeCrudService.create(new OidcUserTransformer(user));
		}
		
		return entity;
	}

}
