package com.zoostarinc.employee.dao.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.zoostarinc.timesheet.model.Timesheet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "TIMESHEET")
public class TimesheetEntity extends Timesheet {

	private UUID id;
	
	private EmployeeEntity employee;

	public TimesheetEntity(UUID id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	public UUID getId() {
		return this.id;
	}

	@Override
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	public EmployeeEntity getEmployee() {
		return employee;
	}
	
	@Override
	@Column(name = "WEEK_ENDING")
	public LocalDate getWeekEnding() {
		return super.getWeekEnding();
	}
	
}
