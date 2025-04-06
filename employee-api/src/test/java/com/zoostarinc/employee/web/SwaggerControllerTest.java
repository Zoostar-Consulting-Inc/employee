package com.zoostarinc.employee.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.zoostarinc.employee.config.AbstractTestHarness;

class SwaggerControllerTest extends AbstractTestHarness {

	@Test
	void testCreate200() throws Exception {
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

}
