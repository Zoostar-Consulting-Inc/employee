package com.zoostarinc.employee.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class EmployeeApiConfig {

	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(new Info().title("Employee CRUD API"));
	}

}
