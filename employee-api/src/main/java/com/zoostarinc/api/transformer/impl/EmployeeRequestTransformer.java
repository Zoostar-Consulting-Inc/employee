package com.zoostarinc.api.transformer.impl;

import org.springframework.util.StringUtils;

import com.zoostarinc.api.request.EmployeeRequest;
import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.employee.service.impl.DefaultEmployeeCrudService;

import lombok.AllArgsConstructor;
import lombok.ToString;
import net.zoostar.common.transform.Transformer;

@ToString
@AllArgsConstructor
public class EmployeeRequestTransformer implements Transformer<Employee> {
	
	private final EmployeeRequest request;
	
	@Override
	public Employee transform() {
		if(!StringUtils.hasText(request.getEmail())) {
			throw new IllegalArgumentException(DefaultEmployeeCrudService.REQUIRED_FIELD_MISSING_ERROR_MSG);
		}
		return request;
	}

}
