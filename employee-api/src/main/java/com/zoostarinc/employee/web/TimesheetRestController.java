package com.zoostarinc.employee.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zoostarinc.employee.service.TimesheetWorkflowService;
import com.zoostarinc.employee.transformer.impl.OidcUserTransformer;
import com.zoostarinc.timesheet.model.Timesheet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@RestController
@AllArgsConstructor
@RequestMapping("/timesheet")
public class TimesheetRestController {

	private final TimesheetWorkflowService timesheetWorkflowManager;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Timesheet> getNew(@AuthenticationPrincipal DefaultOidcUser user) {
		return ResponseEntity.ok(timesheetWorkflowManager.newTimesheet(new OidcUserTransformer(user)));
	}
}
