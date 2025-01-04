package com.zoostarinc.employee.api.transformer.impl;

import org.springframework.util.StringUtils;

import com.zoostarinc.employee.api.request.EmployeeRequest;
import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.employee.service.impl.EmployeeServiceImpl;

import lombok.AllArgsConstructor;
import lombok.ToString;
import net.zoostar.common.core.Transformer;

@ToString
@AllArgsConstructor
public class EmployeeRequestTransformer implements Transformer<Employee> {
	
	private final EmployeeRequest request;
	
	@Override
	public Employee transform() {
		if(!StringUtils.hasText(request.getUsername())) {
			throw new IllegalArgumentException(EmployeeServiceImpl.REQUIRED_FIELD_MISSING_ERROR_MSG);
		}
		return request;
	}

}
