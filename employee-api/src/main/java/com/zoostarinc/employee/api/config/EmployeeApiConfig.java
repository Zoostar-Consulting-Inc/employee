package com.zoostarinc.employee.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class EmployeeApiConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		registry.addRedirectViewController("/employee-api", "/swagger-ui/index.html");
	}

	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(new Info().title("Employee CRUD API"));
	}

}
