package com.zoostarinc.employee.api.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoostarinc.employee.api.response.EmployeeResponse;
import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.repository.EmployeeRepository;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

	@Autowired
	ObjectMapper om;

	@Autowired
	MockMvc api;

	@MockBean
	EmployeeRepository employeeRepository;

	private static UUID id = UUID.randomUUID();
	private static EmployeeEntity entity;

	@BeforeAll
	static void beforeAll() {
		entity = new EmployeeEntity(id);
		entity.setEmail("devops@zoostar.net");
		entity.setFirstName("Dev");
		entity.setLastName("Ops");
		entity.setUsername("zoostar");
	}

	@Test
	void testGetEmployee200() throws Exception {
		// given
		StringBuilder url = new StringBuilder("/api/retrieveByUsername?username=");
		url.append(entity.getUsername());

		// mock
		when(employeeRepository.findByUsername(entity.getUsername())).thenReturn(Optional.of(entity));

		// when
		var response = api.perform(get(url.toString()).with(oidcLogin()).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		var value = om.readValue(response.getContentAsString(), EmployeeResponse.class);
		assertThat(value).isNotNull();
	}

	@Test
	void testGetEmployee400() throws Exception {
		// given
		StringBuilder url = new StringBuilder("/api/retrieveByUsername?username=");
		url.append("");

		// mock
		when(employeeRepository.findByUsername(entity.getUsername())).thenReturn(Optional.of(entity));

		// when
		var response = api.perform(get(url.toString()).with(oidcLogin()).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testGetNoEmployeeFound400() throws Exception {
		// given
		StringBuilder url = new StringBuilder("/api/retrieveByUsername?username=");
		url.append("zoostar");

		// mock
		when(employeeRepository.findByUsername(entity.getUsername())).thenReturn(Optional.empty());

		// when
		var response = api.perform(get(url.toString()).with(oidcLogin()).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

}
