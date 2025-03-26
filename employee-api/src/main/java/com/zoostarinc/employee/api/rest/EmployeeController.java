package com.zoostarinc.employee.api.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoostarinc.employee.api.response.EmployeeResponse;
import com.zoostarinc.employee.api.transformer.impl.EmployeeModelTransformer;
import com.zoostarinc.employee.service.EmployeeService;

import lombok.AllArgsConstructor;
import net.zoostar.common.aop.Timeable;

@Timeable
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class EmployeeController {

	final ObjectMapper om;

	final EmployeeService employeeManager;

	@GetMapping(path = "/retrieveByUsername", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeResponse> getEmployeeByUsername(@RequestParam String username) {
		return new ResponseEntity<>(
				new EmployeeModelTransformer(employeeManager.retrieveByUsername(username)).transform(), HttpStatus.OK);
	}


}
