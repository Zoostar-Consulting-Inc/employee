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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.repository.EmployeeRepository;
import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TimesheetRestControllerTest {

	private static final Employee EMPLOYEE = new Employee();

	private static EmployeeEntity entity;

	private static OidcUserInfo info;

	private static OidcUser oidcUser;

	@Autowired
	MockMvc endpoint;

	@Autowired
	ObjectMapper om;

	@MockBean
	EmployeeRepository employeeRepository;

	@BeforeAll
	static void beforeAll() {
		EMPLOYEE.setEmail("email");
		EMPLOYEE.setFirstName("First");
		EMPLOYEE.setLastName("Last");

		entity = new EmployeeEntity(UUID.randomUUID());
		entity.setEmail(EMPLOYEE.getEmail());
		entity.setFirstName(EMPLOYEE.getFirstName());
		entity.setLastName(EMPLOYEE.getLastName());

		info = OidcUserInfo.builder().email(EMPLOYEE.getEmail()).givenName(EMPLOYEE.getFirstName())
				.familyName(EMPLOYEE.getLastName()).build();
		oidcUser = new DefaultOidcUser(AuthorityUtils.createAuthorityList("SCOPE_message:read"),
				OidcIdToken.withTokenValue("id-token").claim("sub", "user").build(), info);
	}

	@Test
	void testCreate200() throws Exception {
		// given
		String url = "/timesheet";

		// mock
		when(employeeRepository.findByEmail(EMPLOYEE.getEmail())).thenReturn(Optional.of(entity));

		// when
		var response = endpoint
				.perform(get(url).with(oidcLogin().oidcUser(oidcUser)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		var timesheet = om.readValue(response.getContentAsString(), Timesheet.class);
		log.info("Timesheet: {}", timesheet);

		assertThat(timesheet.getEmployee()).isNotNull();
	}

	@Test
	void testCreate400() throws Exception {
		// given
		String url = "/timesheet";

		// mock
		when(employeeRepository.findByEmail(EMPLOYEE.getEmail())).thenReturn(Optional.of(entity));

		// when
		var response = endpoint
				.perform(get(url).with(oidcLogin().oidcUser(oidcUser)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		var timesheet = om.readValue(response.getContentAsString(), Timesheet.class);
		log.info("Timesheet: {}", timesheet);

		assertThat(timesheet.getEmployee()).isNotNull();
	}

}
