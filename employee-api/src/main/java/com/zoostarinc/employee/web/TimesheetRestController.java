package com.zoostarinc.employee.web;

import java.util.Collection;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.request.TimesheetRequest;
import com.zoostarinc.employee.service.EmployeeService;
import com.zoostarinc.employee.service.TimesheetService;
import com.zoostarinc.employee.service.TimesheetState;
import com.zoostarinc.employee.web.response.TimesheetResponse;
import com.zoostarinc.employee.web.transform.impl.TimesheetEntitiesTransformer;
import com.zoostarinc.employee.web.transform.impl.TimesheetRequestTransformer;
import com.zoostarinc.employee.web.transform.impl.TimesheetTransformer;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.zoostar.common.aop.Timeable;
import net.zoostar.common.core.workflow.WorkflowService;
import net.zoostar.common.web.response.SuccessfulRequestLoggerResponseEntity;

@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping("/timesheet")
public class TimesheetRestController implements ApplicationContextAware {

	private final ObjectMapper om;

	private final EmployeeService employeeManager;

	private final WorkflowService<Timesheet> timesheetWorkflowManager;

	private final TimesheetService<TimesheetEntity> timesheetManager;

	private ApplicationContext applicationContext;

	@Timeable
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TimesheetResponse>> getDrafts(@AuthenticationPrincipal OidcUser user) {
		return ResponseEntity.ok(new TimesheetEntitiesTransformer(employeeManager.retrieveByEmail(user.getEmail()),
				timesheetManager.retrieveByEmployeeAndState(employeeManager.retrieveByEmail(user.getEmail()),
						TimesheetState.DRAFT.name()),
				timesheetWorkflowManager).transform());
	}

	@Timeable
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TimesheetResponse> postTimesheet(@AuthenticationPrincipal OidcUser user,
			@RequestBody TimesheetRequest request) {
		return new SuccessfulRequestLoggerResponseEntity<>(new TimesheetTransformer(
				timesheetWorkflowManager.process(new TimesheetRequestTransformer(
						employeeManager.retrieveByEmail(user.getEmail()), request, timesheetWorkflowManager)),
				timesheetWorkflowManager).transform(), request, om);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
