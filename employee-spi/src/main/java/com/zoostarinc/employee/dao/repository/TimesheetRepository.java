package com.zoostarinc.employee.dao.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.entity.TimesheetEntity;

public interface TimesheetRepository extends CrudRepository<TimesheetEntity, UUID> {

	Optional<TimesheetEntity> findByEmployeeAndWeekEnding(EmployeeEntity employee, LocalDate weekEnding);

}
