package com.zoostarinc.employee.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.repository.EmployeeRepository;
import com.zoostarinc.employee.model.Employee;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractTestHarness {

	protected static OidcUserInfo userInfo;

	protected static OidcUser user;

	protected ObjectMapper om = objectMapper();

	protected Employee employee = employee("devops@zoostar.net", "Dev", "Ops");

	protected EmployeeEntity persistableEmployeeEntity = employeeEntity(null, employee);

	protected EmployeeEntity persistentEmployeeEntity = employeeEntity(UUID.randomUUID(), employee);

	@MockitoBean
	protected EmployeeRepository employeeRepository;

	@Autowired
	protected MockMvc endpoint;

	public static Employee employee(String email, String firstName, String lastName) {
		var employee = new Employee();
		employee.setEmail(email);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		return employee;
	}

	public static EmployeeEntity employeeEntity(UUID id, Employee employee) {
		var entity = new EmployeeEntity(id);
		entity.setEmail(employee.getEmail());
		entity.setFirstName(employee.getFirstName());
		entity.setLastName(employee.getLastName());
		return entity;
	}

	public static ObjectMapper objectMapper() {
		var om = new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return om;
	}

	public static OidcUser testOidcUser(Employee employee) {
		var info = OidcUserInfo.builder().email(employee.getEmail()).givenName(employee.getFirstName())
				.familyName(employee.getLastName()).build();
		return new DefaultOidcUser(AuthorityUtils.createAuthorityList("SCOPE_message:read"),
				OidcIdToken.withTokenValue("id-token").claim("sub", "user").build(), info);

	}

}
