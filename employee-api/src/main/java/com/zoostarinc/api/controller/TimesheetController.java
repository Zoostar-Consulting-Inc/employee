package com.zoostarinc.api.controller;

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;
import com.zoostarinc.timesheet.service.TimesheetCrudService;
import com.zoostarinc.workflow.service.impl.TimesheetWorkflowService;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.audit.Timeable;

@Slf4j
@ToString
@RestController
@RequiredArgsConstructor
public class TimesheetController {

	protected final TimesheetCrudService timesheetCrudManager;
	
	protected final TimesheetWorkflowService timesheetWorkflowManager;

	@Timeable
	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TimesheetEntity>> getDrafts(@AuthenticationPrincipal DefaultOidcUser user) {
		log.info("Get all draft timesheets for user: {}...", user.getGivenName());
		return ResponseEntity.ok(timesheetCrudManager.retrieveDrafts(user));
	}

}
