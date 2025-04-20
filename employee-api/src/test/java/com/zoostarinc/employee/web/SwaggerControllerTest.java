package com.zoostarinc.employee.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.zoostarinc.employee.config.AbstractTestHarness;

class SwaggerControllerTest extends AbstractTestHarness {

	@Test
	void testCreate200() throws Exception {
		// given
		String url = "/";

		// mock
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
		when(employeeRepository.save(persistableEmployeeEntity)).thenReturn(persistentEmployeeEntity);

		// when
		var result = endpoint
				.perform(get(url).with(oidcLogin().oidcUser(testOidcUser(employee))).contentType(MediaType.TEXT_HTML_VALUE))
				.andReturn();

		// then
		assertThat(result).isNotNull();
	}

	@Test
	void testDuplicate200() throws Exception {
		// given
		String url = "/";

		// mock
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(persistentEmployeeEntity));

		// when
		var result = endpoint
				.perform(get(url).with(oidcLogin().oidcUser(testOidcUser(employee))).contentType(MediaType.TEXT_HTML_VALUE))
				.andReturn();

		// then
		assertThat(result).isNotNull();
	}

	@Test
	void testCreate400NullUser() throws Exception {
		employee.setEmail("");
		
		// given
		String url = "/";

		// when
		var response = endpoint.perform(get(url).with(oidcLogin().oidcUser(testOidcUser(employee)))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testCreate200EmptyFirstName() throws Exception {
		employee.setFirstName(null);
		
		// given
		String url = "/";

		// mock
		when(employeeRepository.save(persistableEmployeeEntity)).thenReturn(persistentEmployeeEntity);

		// when
		var result = endpoint
				.perform(get(url).with(oidcLogin().oidcUser(testOidcUser(employee))).contentType(MediaType.TEXT_HTML_VALUE))
				.andReturn();

		// then
		assertThat(result).isNotNull();
	}

	@Test
	void testCreate200EmptyLastName() throws Exception {
		employee.setLastName(null);
		
		// given
		String url = "/";

		// mock
		when(employeeRepository.save(persistableEmployeeEntity)).thenReturn(persistentEmployeeEntity);

		// when
		var result = endpoint
				.perform(get(url).with(oidcLogin().oidcUser(testOidcUser(employee))).contentType(MediaType.TEXT_HTML_VALUE))
				.andReturn();

		// then
		assertThat(result).isNotNull();
	}
}
