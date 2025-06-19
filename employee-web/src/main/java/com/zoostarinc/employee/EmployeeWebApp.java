package com.zoostarinc.employee;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import lombok.Generated;

@Generated
@EnableCaching
@EnableWebSecurity
@SpringBootApplication
@EnableLoadTimeWeaving
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = { "net.zoostar", "com.zoostarinc" })
public class EmployeeWebApp {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeWebApp.class, args);
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity hs) throws Exception {
		return hs.cors(withDefaults()).csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated()).oauth2Login(withDefaults())
				.build();
	}

}
