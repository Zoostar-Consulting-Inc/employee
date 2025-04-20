package com.zoostarinc.employee.web;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zoostarinc.employee.dao.entity.TimesheetEntity;
import com.zoostarinc.employee.request.TimesheetRequest;
import com.zoostarinc.employee.service.TimesheetWorkflowService;
import com.zoostarinc.employee.web.transformer.impl.TimesheetResponseTransformer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@RestController
@AllArgsConstructor
@RequestMapping("/timesheet")
public class TimesheetRestController {

	private final TimesheetWorkflowService timesheetWorkflowManager;

	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<TimesheetEntity> getNew(@AuthenticationPrincipal OidcUser user) {
		return ResponseEntity.ok(timesheetWorkflowManager.newTimesheet(user.getEmail()));
	}

	@PostMapping(path = "/process", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TimesheetEntity> postForProcessing(@AuthenticationPrincipal OidcUser user,
			@RequestBody TimesheetRequest request) {
		return ResponseEntity
				.ok(new TimesheetResponseTransformer(timesheetWorkflowManager.process(user.getEmail(), request))
						.transform());
	}

}
