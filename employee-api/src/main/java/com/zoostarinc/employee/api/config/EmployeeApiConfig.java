package com.zoostarinc.employee.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class EmployeeApiConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		String swaggerUrl = "/swagger-ui/index.html";
		log.info("Swagger URL: {}", swaggerUrl);
		registry.addRedirectViewController("/", swaggerUrl);
	}

}
