package com.zoostarinc.employee.dao.entity;

import java.util.Objects;
import java.util.UUID;

import com.zoostarinc.employee.model.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Entity
@NoArgsConstructor
@Table(name = "EMPLOYEE")
@ToString(callSuper = true)
public class EmployeeEntity extends Employee {

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
	public int hashCode() {
		return Objects.hash(getEmail());
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
		return Objects.equals(getEmail(), other.getEmail());
	}

}
