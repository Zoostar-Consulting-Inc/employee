package com.zoostarinc.employee.web;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SwaggerController {

	@GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
	public RedirectView getSwaggerUI(@AuthenticationPrincipal DefaultOidcUser user) {
		log.info("Welcome: {}", user.getGivenName());
		String swaggerUrl = "/swagger-ui/index.html";
		log.info("Loading Swagger URL at: {}...", swaggerUrl);
		return new RedirectView(swaggerUrl);
	}

}
