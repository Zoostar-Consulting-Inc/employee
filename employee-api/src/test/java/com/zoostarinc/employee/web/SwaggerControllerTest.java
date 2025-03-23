package com.zoostarinc.employee.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SwaggerControllerTest {

	@Autowired
	MockMvc endpoint;

	@Test
	void testGetSwaggerUI() throws Exception {
		// given
		String url = "/";

		// when
		var result = endpoint.perform(get(url).with(oidcLogin()).contentType(MediaType.TEXT_HTML_VALUE)).andReturn();

		// then
		assertThat(result).isNotNull();
	}

}
