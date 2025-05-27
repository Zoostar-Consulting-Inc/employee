package com.zoostarinc.employee.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ApiConfigurer {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity hs) throws Exception {
		return hs.cors(withDefaults()).csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated()).oauth2Login(Customizer.withDefaults())
				.build();
	}
	
	@Bean
	ObjectMapper objectMapper() {
		ObjectMapper bean = new ObjectMapper();
		bean.registerModule(new JavaTimeModule());
		bean.registerModule(new Jdk8Module());
		SimpleModule module = new SimpleModule();
//		module.addDeserializer(TimesheetState.class, new JsonDeserializer<TimesheetState>() {
//			@Override
//			public TimesheetState deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//				return TimesheetState.valueOf(p.getText());
//			}
//		});
		bean.registerModule(module);
		bean.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		bean.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return bean;
	}

}