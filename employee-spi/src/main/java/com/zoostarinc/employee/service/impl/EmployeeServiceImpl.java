package com.zoostarinc.employee.service.impl;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.repository.EmployeeRepository;
import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.employee.service.EmployeeService;
import com.zoostarinc.employee.transform.impl.EmployeeTransformer;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.transform.Transformer;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	public static final String REQUIRED_FIELD_MISSING_ERROR_MSG = "Required field [username] is missing in request!";

	final EmployeeRepository employeeRepository;

	@Override
	@Transactional
	public EmployeeEntity create(Transformer<Employee> transformer) {
		var employee = transformer.transform();
		var row = employeeRepository.findByEmail(employee.getEmail());
		if (row.isPresent()) {
			throw new DuplicateKeyException("Employee exists with given email!");
		}

		log.info("Creating new employee: {}", employee);
		return employeeRepository.save(new EmployeeTransformer(employee).transform());
	}

	@Override
	public EmployeeEntity retrieveByEmail(String email) {
		if (!StringUtils.hasText(email)) {
			throw new IllegalArgumentException(REQUIRED_FIELD_MISSING_ERROR_MSG);
		}

		return employeeRepository.findByEmail(email)
				.orElseThrow(() -> new EmptyResultDataAccessException("No employee found by given username!", 1));
	}

	@Override
	@Transactional
	public EmployeeEntity update(Transformer<Employee> transformer) {
		var employee = transformer.transform();
		var entity = retrieveByEmail(employee.getEmail());
		log.info("Updating existing employee: {}", entity);
		entity.setFirstName(employee.getFirstName());
		entity.setLastName(employee.getLastName());
		return entity;
	}

}
