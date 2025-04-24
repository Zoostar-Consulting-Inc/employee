package com.zoostarinc.employee.service;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public interface SwaggerService {

	String getRedirectUrl(OidcUser user);

}
