package com.zoostarinc.employee.service;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;

public interface EmployeeCrudService {
	EmployeeEntity retrieveByEmail(String email);
}
