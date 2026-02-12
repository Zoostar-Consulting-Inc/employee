package com.zoostarinc.employee.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.repository.EmployeeRepository;
import com.zoostarinc.employee.service.EmployeeCrudService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultEmployeeCrudService implements EmployeeCrudService {

	public static final String REQUIRED_FIELD_MISSING_ERROR_MSG = "Required field [username] is missing in request!";

	private final EmployeeRepository employeeRepository;

	@Override
	@Cacheable("retrieveByEmail")
	public EmployeeEntity retrieveByEmail(String email) {
		if (!StringUtils.hasText(email)) {
			throw new IllegalArgumentException(REQUIRED_FIELD_MISSING_ERROR_MSG);
		}

		return employeeRepository.findByEmail(email)
				.orElseThrow(() -> new EmptyResultDataAccessException("No employee found by given email!", 1));
	}

}
