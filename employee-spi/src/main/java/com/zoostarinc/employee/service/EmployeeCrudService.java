package com.zoostarinc.employee.service;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.model.Employee;

import net.zoostar.common.transform.Transformer;

public interface EmployeeCrudService {
	EmployeeEntity create(Transformer<Employee> transformer);
	EmployeeEntity retrieveByEmail(String email);
}
