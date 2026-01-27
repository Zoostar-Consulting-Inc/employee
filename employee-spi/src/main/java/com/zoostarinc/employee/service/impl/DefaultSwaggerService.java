package com.zoostarinc.employee.service.impl;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.employee.service.EmployeeCrudService;
import com.zoostarinc.employee.service.SwaggerService;
import com.zoostarinc.employee.transformer.impl.OidcUserTransformer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.transform.Transformer;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultSwaggerService implements SwaggerService {

	private static final String SWAGGER_URL = "swagger-ui/index.html";
	
	final EmployeeCrudService employeeCrudManager;

	@Override
	public String getRedirectUrl(OidcUser user) {
		try {
			createIfNotFound(new OidcUserTransformer(user));
			log.info("Nice to meet you {}!", user.getName());
		} catch (DuplicateKeyException e) {
			log.info("Welcome back {}!", user.getGivenName());
		}

		log.info("Loading Swagger UI at: {}...", SWAGGER_URL);
		return SWAGGER_URL;
	}
	
	@Override
	public EmployeeEntity createIfNotFound(Transformer<Employee> transformer) {
		EmployeeEntity entity = null;
		var employee = transformer.transform();
		var email = employee.getEmail();
		
		try {
			entity = employeeCrudManager.retrieveByEmail(email);
		} catch (EmptyResultDataAccessException e) {
			log.trace("{}", e.getMessage());
			entity = new EmployeeEntity(email);
		}
		
		return entity;
	}

}