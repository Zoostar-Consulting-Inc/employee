package com.zoostarinc.employee.dao.entity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "TIMESHEET")
public class TimesheetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	private EmployeeEntity employee;

	@Column(name = "HOURS")
	private Integer hours;

	@Column(name = "WEEK_ENDING")
	private LocalDate weekEnding;

//	@Enumerated(EnumType.STRING)
	@JoinColumn(name = "STATE")
	private String state;
	
	@Column(name = "UPDATED_AT")
	private OffsetDateTime updatedAt;

	public TimesheetEntity(UUID id) {
		this.id = id;
	}

}