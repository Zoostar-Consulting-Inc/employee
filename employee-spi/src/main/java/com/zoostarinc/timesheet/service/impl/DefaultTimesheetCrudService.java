package com.zoostarinc.timesheet.service.impl;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;
import com.zoostarinc.timesheet.dao.repository.TimesheetRepository;
import com.zoostarinc.timesheet.service.TimesheetCrudService;
import com.zoostarinc.workflow.state.TimesheetStateDraft;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.zoostar.common.transform.Transformer;

@Getter
@Service
@ToString
@RequiredArgsConstructor
public class DefaultTimesheetCrudService implements TimesheetCrudService {

	private final TimesheetRepository timesheetRepository;
	
	@Override
	public TimesheetEntity retrieveByEmailAndWeekEnding(String email, LocalDate weekEnding) {
		return timesheetRepository.findByEmailAndWeekEnding(email, weekEnding).orElseThrow();
	}

	@Override
	public TimesheetEntity create(Transformer<TimesheetEntity> transformer) {
		return timesheetRepository.save(transformer.transform());
	}

	@Override
	public Collection<TimesheetEntity> retrieveDrafts(DefaultOidcUser user) {
		return timesheetRepository.findByEmailAndState(user.getEmail(), TimesheetStateDraft.NAME);
	}

}
