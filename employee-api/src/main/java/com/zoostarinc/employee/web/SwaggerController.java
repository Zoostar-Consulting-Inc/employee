package com.zoostarinc.employee.web;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.zoostarinc.employee.service.SwaggerService;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Generated
@Controller
@AllArgsConstructor
public class SwaggerController {

	final SwaggerService swaggerManager;
	
	@GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
	public RedirectView getSwaggerUI(@AuthenticationPrincipal DefaultOidcUser user) {
		return new RedirectView(swaggerManager.getRedirectUrl(user));
	}

}
