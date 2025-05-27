package com.zoostarinc.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.Generated;

@Generated
@EnableCaching
@EnableWebSecurity
@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "net.zoostar", "com.zoostarinc" })
public class EmployeeApi extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApi.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(EmployeeApi.class);
	}

	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(new Info().title("Employee CRUD API"));
	}

}
