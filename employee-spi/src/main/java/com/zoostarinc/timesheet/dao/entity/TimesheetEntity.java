package com.zoostarinc.timesheet.dao.entity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "TIMESHEET")
public class TimesheetEntity implements Persistable<UUID> {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String email;
	
	@Column(name = "WEEK_ENDING")
	private LocalDate weekEnding;
	
	private int hours;
	
	private String state;

	@Override
	public boolean isNew() {
		return id == null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, weekEnding);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TimesheetEntity)) {
			return false;
		}
		TimesheetEntity other = (TimesheetEntity) obj;
		return Objects.equals(email, other.email) && Objects.equals(weekEnding, other.weekEnding);
	}
}
