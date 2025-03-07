package com.zoostarinc.employee.service;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.model.Employee;

import net.zoostar.common.core.Transformer;

public interface EmployeeService {

	EmployeeEntity create(Transformer<Employee> transformer);

	EmployeeEntity retrieveByUsername(String username);

	EmployeeEntity update(Transformer<Employee> transformer);

}
