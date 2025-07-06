package com.zoostarinc.employee.api.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.zoostarinc.employee.api.request.EmployeeRequest;
import com.zoostarinc.employee.api.response.EmployeeResponse;
import com.zoostarinc.employee.config.AbstractTestHarness;
import com.zoostarinc.employee.dao.entity.EmployeeEntity;

class EmployeeControllerTest extends AbstractTestHarness {

	private static UUID id = UUID.randomUUID();
	private static EmployeeEntity entity;

	@BeforeAll
	static void beforeAll() {
		entity = new EmployeeEntity(id);
		entity.setEmail("devops@zoostar.net");
		entity.setFirstName("Dev");
		entity.setLastName("Ops");
	}

	@Test
	void testPostEmployee201() throws Exception {
		// given
		String url = "/api/create";
		var request = new EmployeeRequest();
		request.setEmail(entity.getEmail());
		request.setFirstName(entity.getFirstName());
		request.setLastName(entity.getLastName());

		// mock
		when(employeeRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
		when(employeeRepository.save(entity)).thenReturn(entity);

		// when
		var response = endpoint
				.perform(post(url).with(oidcLogin().oidcUser(testOidcUser(employee))).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).content(om.writeValueAsString(request)))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		var value = om.readValue(response.getContentAsString(), EmployeeResponse.class);
		assertThat(value).isNotNull();
	}

	@Test
	void testPostEmptyUsername400() throws Exception {
		// given
		String url = "/api/create";
		var request = new EmployeeRequest();
		request.setEmail("");

		// when
		var response = endpoint
				.perform(post(url).with(oidcLogin()).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).content(om.writeValueAsString(request)))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testPostDuplicateEmployee400() throws Exception {
		// given
		String url = "/api/create";
		var request = new EmployeeRequest();
		request.setEmail(entity.getEmail());
		request.setFirstName(entity.getFirstName());
		request.setLastName(entity.getLastName());

		// mock
		when(employeeRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(entity));

		// when
		var response = endpoint
				.perform(post(url).with(oidcLogin()).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).content(om.writeValueAsString(request)))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testGetEmployee200() throws Exception {
		// given
		StringBuilder url = new StringBuilder("/api/retrieveByEmail?email=");
		url.append(entity.getEmail());

		// mock
		when(employeeRepository.findByEmail(entity.getEmail())).thenReturn(Optional.of(entity));

		// when
		var response = endpoint.perform(get(url.toString()).with(oidcLogin()).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

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
		when(employeeRepository.findByEmail(entity.getEmail())).thenReturn(Optional.of(entity));

		// when
		var response = endpoint.perform(get(url.toString()).with(oidcLogin()).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testGetNoEmployeeFound400() throws Exception {
		// given
		StringBuilder url = new StringBuilder("/api/retrieveByUsername?username=");
		url.append("zoostar");

		// mock
		when(employeeRepository.findByEmail(entity.getEmail())).thenReturn(Optional.empty());

		// when
		var response = endpoint.perform(get(url.toString()).with(oidcLogin()).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testPutEmployee200() throws Exception {
		// given
		String url = "/api/update";
		var request = new EmployeeRequest();
		request.setEmail(entity.getEmail());
		request.setFirstName(entity.getFirstName());
		request.setLastName(entity.getLastName());

		// mock
		when(employeeRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(entity));
		when(employeeRepository.save(entity)).thenReturn(entity);

		// when
		var response = endpoint.perform(
				put(url).with(oidcLogin().oidcUser(testOidcUser(employee))).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).content(om.writeValueAsString(request)))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		var value = om.readValue(response.getContentAsString(), EmployeeResponse.class);
		assertThat(value).isNotNull();
	}

}
