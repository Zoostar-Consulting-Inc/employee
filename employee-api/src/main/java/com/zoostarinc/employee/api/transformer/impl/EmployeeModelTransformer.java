package com.zoostarinc.employee.api.transformer.impl;

import com.zoostarinc.employee.api.response.EmployeeResponse;
import com.zoostarinc.employee.model.Employee;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.core.Transformer;

@Slf4j
@ToString
@AllArgsConstructor
public class EmployeeModelTransformer implements Transformer<EmployeeResponse> {

	private final Employee employee;

	@Override
	public EmployeeResponse transform() {
		var response = new EmployeeResponse();
		response.setEmail(employee.getEmail());
		response.setFirstName(employee.getFirstName());
		response.setLastName(employee.getLastName());
		response.setUsername(employee.getUsername());
		log.info("Employee Response: {}", response);
		return response;
	}

}
