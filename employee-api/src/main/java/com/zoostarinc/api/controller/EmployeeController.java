package com.zoostarinc.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoostarinc.api.response.EmployeeResponse;
import com.zoostarinc.api.transformer.impl.EmployeeModelTransformer;
import com.zoostarinc.employee.service.EmployeeCrudService;

import lombok.RequiredArgsConstructor;
import net.zoostar.common.audit.Timeable;

@Timeable
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmployeeController {

	protected final ObjectMapper om;

	protected final EmployeeCrudService employeeManager;

	@GetMapping(path = "/retrieveByEmail", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeResponse> getByEmail(@RequestParam String email) {
		return new ResponseEntity<>(
				new EmployeeModelTransformer(employeeManager.retrieveByEmail(email)).transform(), HttpStatus.OK);
	}

}
