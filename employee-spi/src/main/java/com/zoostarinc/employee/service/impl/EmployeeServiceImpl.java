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
import net.zoostar.common.core.Transformer;

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
		var value = employeeRepository.findByUsername(employee.getUsername());
		if (value.isPresent()) {
			throw new DuplicateKeyException("Employee exists with given username!");
		}

		log.info("Creating new employee: {}", employee);
		return employeeRepository.save(new EmployeeTransformer(employee).transform());
	}

	@Override
	public EmployeeEntity retrieveByUsername(String username) {
		if (!StringUtils.hasText(username)) {
			throw new EmptyResultDataAccessException(REQUIRED_FIELD_MISSING_ERROR_MSG, 1);
		}

		return employeeRepository.findByUsername(username)
				.orElseThrow(() -> new EmptyResultDataAccessException("No employee found by given username!", 1));
	}

	@Override
	@Transactional
	public EmployeeEntity update(Transformer<Employee> transformer) {
		var employee = transformer.transform();
		var entity = retrieveByUsername(employee.getUsername());
		log.info("Updating existing employee: {}", entity);
		entity.setEmail(employee.getEmail());
		entity.setFirstName(employee.getFirstName());
		entity.setLastName(employee.getLastName());
		return entity;
	}

}
