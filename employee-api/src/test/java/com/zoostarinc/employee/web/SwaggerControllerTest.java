package com.zoostarinc.employee.web;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.repository.EmployeeRepository;

@SpringBootTest
@AutoConfigureMockMvc
class SwaggerControllerTest {

	@Autowired
	MockMvc endpoint;

	@MockBean
	EmployeeRepository employeeRepository;

	private static EmployeeEntity entity;

	@BeforeAll
	static void beforeAll() {
		entity = new EmployeeEntity(UUID.randomUUID());
		entity.setEmail("email");
		entity.setFirstName("First");
		entity.setLastName("Last");
		entity.setUsername("username");
	}

	@Test
	void testCreate200() throws Exception {
		// given
		String url = "/";

		// when
		var result = endpoint.perform(get(url).with(oidcLogin()).contentType(MediaType.TEXT_HTML_VALUE)).andReturn();

		// then
		assertThat(result).isNotNull();
	}

	@Test
	void testDuplicate200() throws Exception {
		// given
		String url = "/";

		// mock
		when(employeeRepository.findByUsername(null)).thenReturn(Optional.of(entity));

		// when
		var result = endpoint.perform(get(url).with(oidcLogin()).contentType(MediaType.TEXT_HTML_VALUE)).andReturn();

		// then
		assertThat(result).isNotNull();
		assertThat(entity.isNew()).isFalse();
		
		var employee = new EmployeeEntity();
		employee.setUsername("username");
		assertThat(employee.isNew()).isTrue();
		assertThat(employee).isEqualTo(entity).hasSameHashCodeAs(entity);
		assertThat(employee).isEqualTo(employee);
	}

}
