package com.zoostarinc.employee.service.impl;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.zoostarinc.employee.service.EmployeeCrudService;
import com.zoostarinc.employee.service.SwaggerService;
import com.zoostarinc.employee.transformer.impl.OidcUserTransformer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultSwaggerService implements SwaggerService {

	private static final String SWAGGER_URL = "swagger-ui/index.html";
	
	final EmployeeCrudService employeeManager;

	@Override
	public String getRedirectUrl(OidcUser user) {
		try {
			var entity = employeeManager.create(new OidcUserTransformer(user));
			log.info("Nice to meet you {}!", entity.getFirstName());
		} catch (DuplicateKeyException e) {
			log.info("Welcome back {}!", user.getGivenName());
		}

		log.info("Loading Swagger UI at: {}...", SWAGGER_URL);
		return SWAGGER_URL;
	}

}