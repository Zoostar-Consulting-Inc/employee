package com.zoostarinc.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoostarinc.employee.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import net.zoostar.common.audit.Timeable;

@Timeable
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmployeeController {

	protected final ObjectMapper om;

	protected final EmployeeService employeeManager;

//	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Employee created successfully."),
//			@ApiResponse(responseCode = "400", description = "Bad data provided. Fix and retry.") })
//	@PostMapping(path = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
//	public ResponseEntity<EmployeeResponse> post(@RequestBody EmployeeRequest request) {
//		return new SuccessfulRequestLoggerResponseEntity<>(
//				new EmployeeModelTransformer(employeeManager.create(new EmployeeRequestTransformer(request)))
//						.transform(),
//				request, om, HttpStatus.CREATED);
//	}
//
//	@GetMapping(path = "/retrieveByEmail", produces = APPLICATION_JSON_VALUE)
//	public ResponseEntity<EmployeeResponse> getByEmail(@RequestParam String email) {
//		return new ResponseEntity<>(
//				new EmployeeModelTransformer(employeeManager.retrieveByEmail(email)).transform(), HttpStatus.OK);
//	}

}
