package com.zoostarinc.employee.api.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoostarinc.employee.api.request.EmployeeRequest;
import com.zoostarinc.employee.api.response.EmployeeResponse;
import com.zoostarinc.employee.api.transformer.impl.EmployeeModelTransformer;
import com.zoostarinc.employee.api.transformer.impl.EmployeeRequestTransformer;
import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.employee.service.EmployeeService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.zoostar.common.core.Transformer;
import net.zoostar.common.web.response.SuccessfulRequestLoggerResponseEntity;

@RestController
public class EmployeeController {

	@Autowired
	ObjectMapper om;
	
	@Autowired
	EmployeeService employeeManager;

	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Employee created successfully."),
			@ApiResponse(responseCode = "400", description = "Bad data provided. Fix and retry.") })
	@PostMapping(path = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeResponse> postEmployee(@RequestBody EmployeeRequest request) {
		var entity = employeeManager.create(new EmployeeRequestTransformer(request));
		var response = new EmployeeModelTransformer(entity).transform();
		return new SuccessfulRequestLoggerResponseEntity<>(response, request, om);
	}

	@GetMapping(path = "/retrieve", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeResponse> getEmployeeByUsername(@RequestParam String username) {
		return new ResponseEntity<>(
				new EmployeeModelTransformer(employeeManager.retrieveByUsername(username)).transform(),
				HttpStatus.OK);
	}

	@PutMapping(path = "/update", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeResponse> putEmployee(@RequestBody EmployeeRequest request) {
		Transformer<Employee> transformer = new EmployeeRequestTransformer(request);
		var employee = employeeManager.update(transformer);
		return new ResponseEntity<>(new EmployeeModelTransformer(employee).transform(), HttpStatus.OK);
	}

}
