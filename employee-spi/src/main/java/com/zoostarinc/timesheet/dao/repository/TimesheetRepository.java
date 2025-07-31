package com.zoostarinc.timesheet.dao.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;

public interface TimesheetRepository extends CrudRepository<TimesheetEntity, UUID> 	{

	Optional<TimesheetEntity> findByEmailAndWeekEnding(String email, LocalDate weekEnding);

	Collection<TimesheetEntity> findByEmailAndState(String email, String state);

}
