package com.zoostarinc.employee.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.repository.EmployeeRepository;
import com.zoostarinc.employee.model.Employee;

import net.zoostar.common.core.workflow.State;
import net.zoostar.common.core.workflow.timesheet.state.StateNew;

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

	@MockBean
	protected EmployeeRepository employeeRepository;

	@Autowired
	protected MockMvc endpoint;

	protected Employee employee(String email, String firstName, String lastName) {
		return new Employee(email, firstName, lastName);
	}

	protected EmployeeEntity employeeEntity(UUID id, Employee employee) {
		var entity = new EmployeeEntity(id);
		entity.setEmail(employee.getEmail());
		entity.setFirstName(employee.getFirstName());
		entity.setLastName(employee.getLastName());
		return entity;
	}

	protected ObjectMapper objectMapper() {
		var bean = new ObjectMapper();
		bean.registerModule(new JavaTimeModule()).registerModule(new Jdk8Module())
				.registerModule(new SimpleModule().addAbstractTypeMapping(State.class, StateNew.class));
		bean.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return bean;
	}

	protected OidcUser testOidcUser(Employee employee) {
		var info = OidcUserInfo.builder().email(employee.getEmail()).givenName(employee.getFirstName())
				.familyName(employee.getLastName()).build();
		return new DefaultOidcUser(AuthorityUtils.createAuthorityList("SCOPE_message:read"),
				OidcIdToken.withTokenValue("id-token").claim("sub", "user").build(), info);

	}

}
