package com.zoostarinc.employee.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class EmployeeConfigurer {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity hs) throws Exception {
		return hs.cors(withDefaults()).csrf(AbstractHttpConfigurer::disable).build();
	}

}