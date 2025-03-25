package com.zoostarinc.employee.web;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.zoostarinc.employee.service.EmployeeService;
import com.zoostarinc.employee.transform.impl.OidcUserTransformer;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Generated
@Controller
@AllArgsConstructor
public class SwaggerController {

	final EmployeeService employeeManager;
	
	@GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
	public RedirectView getSwaggerUI(@AuthenticationPrincipal DefaultOidcUser user) {
		try {
			var entity = employeeManager.create(new OidcUserTransformer(user));
			log.info("Nice to meet you {}!", entity.getFirstName());
		} catch(DuplicateKeyException e) {
			log.info("Welcome back {}!", user.getGivenName());
		}
		
		String swaggerUrl = "swagger-ui/index.html";
		log.info("Loading Swagger URL at: {}...", swaggerUrl);
		return new RedirectView(swaggerUrl);
	}

}
