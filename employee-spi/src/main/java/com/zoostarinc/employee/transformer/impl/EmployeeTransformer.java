package com.zoostarinc.employee.transformer.impl;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.model.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.core.Transformer;

@Slf4j
@Getter
@AllArgsConstructor
public class EmployeeTransformer implements Transformer<EmployeeEntity> {

	private final Employee employee;

	@Override
	public EmployeeEntity transform() {
		var entity = new EmployeeEntity();
		entity.setEmail(employee.getEmail());
		entity.setFirstName(employee.getFirstName());
		entity.setLastName(employee.getLastName());
		log.info("Employee Entity: {}", entity);
		return entity;
	}

}
