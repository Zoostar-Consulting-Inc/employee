package com.zoostarinc.employee.dao.entity;

import java.util.UUID;

import org.springframework.data.domain.Persistable;

import com.zoostarinc.employee.model.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Entity
@NoArgsConstructor
@Table(name = "EMPLOYEE")
@ToString(callSuper = true)
public class EmployeeEntity extends Employee implements Persistable<UUID> {

	private UUID id;

	public EmployeeEntity(UUID id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	public UUID getId() {
		return this.id;
	}

	@Override
	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return super.getEmail();
	}

	@Override
	@Column(name = "FIRST_NAME", length = 50)
	public String getFirstName() {
		return super.getFirstName();
	}

	@Override
	@Column(name = "LAST_NAME", length = 50)
	public String getLastName() {
		return super.getLastName();
	}
	
	@Override
	@Transient
	public boolean isNew() {
		return id == null;
	}

}
