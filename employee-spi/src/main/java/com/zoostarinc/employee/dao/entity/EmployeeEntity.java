package com.zoostarinc.employee.dao.entity;

import java.util.Objects;
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
import lombok.Setter;
import lombok.ToString;

@Setter
@Entity
@Table(name = "EMPLOYEE")
@ToString(callSuper = true)
public class EmployeeEntity extends Employee implements Persistable<UUID> {

	private UUID id;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	public UUID getId() {
		return this.id;
	}

	@Override
	public String getEmail() {
		return super.getEmail();
	}

	@Override
	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return super.getFirstName();
	}

	@Override
	@Column(name = "LAST_NAME")
	public String getLastName() {
		return super.getLastName();
	}

	@Override
	public String getUsername() {
		return super.getUsername();
	}
	
	@Override
	@Transient
	public boolean isNew() {
		return id == null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUsername());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof EmployeeEntity)) {
			return false;
		}
		EmployeeEntity other = (EmployeeEntity) obj;
		return Objects.equals(getUsername(), other.getUsername());
	}

}
