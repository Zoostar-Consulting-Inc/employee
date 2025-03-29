package com.zoostarinc.employee.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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

	private static OidcUserInfo info;

	private static OidcUser oidcUser;

	@BeforeAll
	static void beforeAll() {
		entity = new EmployeeEntity(UUID.randomUUID());
		entity.setEmail(" Email ");
		entity.setFirstName(" First ");
		entity.setLastName(" Last ");

		info = OidcUserInfo.builder().email("devops@zoostar.net").givenName("Dev").familyName("Ops").build();
		oidcUser = new DefaultOidcUser(AuthorityUtils.createAuthorityList("SCOPE_message:read"),
				OidcIdToken.withTokenValue("id-token").claim("sub", "user").build(), info);
	}

	@Test
	void testCreate200() throws Exception {
		// given
		String url = "/";

		// when
		var result = endpoint
				.perform(get(url).with(oidcLogin().oidcUser(oidcUser)).contentType(MediaType.TEXT_HTML_VALUE))
				.andReturn();

		// then
		assertThat(result).isNotNull();
	}

	@Test
	void testDuplicate200() throws Exception {
		// given
		String url = "/";

		// mock
		when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(entity));

		// when
		var result = endpoint
				.perform(get(url).with(oidcLogin().oidcUser(oidcUser)).contentType(MediaType.TEXT_HTML_VALUE))
				.andReturn();

		// then
		assertThat(result).isNotNull();
		assertThat(entity.isNew()).isFalse();

		var employee = new EmployeeEntity();
		employee.setEmail("email");
		assertThat(employee.isNew()).isTrue();
	}

}
