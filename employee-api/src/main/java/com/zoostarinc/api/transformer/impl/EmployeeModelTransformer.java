package com.zoostarinc.api.transformer.impl;

import com.zoostarinc.api.response.EmployeeResponse;
import com.zoostarinc.employee.model.Employee;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.transform.Transformer;

@Slf4j
@ToString
@AllArgsConstructor
public class EmployeeModelTransformer implements Transformer<EmployeeResponse> {

	private final Employee employee;

	@Override
	public EmployeeResponse transform() {
		var response = new EmployeeResponse();
		response.setEmail(employee.getEmail());
		log.info("Employee Response: {}", response);
		return response;
	}

}
