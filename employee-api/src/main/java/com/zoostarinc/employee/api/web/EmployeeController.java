package com.zoostarinc.employee.api.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
import lombok.RequiredArgsConstructor;
import net.zoostar.common.audit.Timeable;
import net.zoostar.common.transform.Transformer;
import net.zoostar.common.web.response.SuccessfulRequestLoggerResponseEntity;

@Timeable
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmployeeController {

	final ObjectMapper om;

	final EmployeeService employeeManager;

	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Employee created successfully."),
			@ApiResponse(responseCode = "400", description = "Bad data provided. Fix and retry.") })
	@PostMapping(path = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeResponse> postEmployee(@RequestBody EmployeeRequest request) {
		return new SuccessfulRequestLoggerResponseEntity<>(
				new EmployeeModelTransformer(employeeManager.create(new EmployeeRequestTransformer(request)))
						.transform(),
				request, om, HttpStatus.CREATED);
	}

	@GetMapping(path = "/retrieveByEmail", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeResponse> getEmployeeByUsername(@RequestParam String email) {
		return new ResponseEntity<>(
				new EmployeeModelTransformer(employeeManager.retrieveByEmail(email)).transform(), HttpStatus.OK);
	}

	@PutMapping(path = "/update", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeResponse> putEmployee(@RequestBody EmployeeRequest request) {
		Transformer<Employee> transformer = new EmployeeRequestTransformer(request);
		var employee = employeeManager.update(transformer);
		return new ResponseEntity<>(new EmployeeModelTransformer(employee).transform(), HttpStatus.OK);
	}

}
