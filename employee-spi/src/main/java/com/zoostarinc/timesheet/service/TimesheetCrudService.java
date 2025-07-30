package com.zoostarinc.timesheet.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;

import net.zoostar.common.transform.Transformer;

public interface TimesheetCrudService {
	TimesheetEntity retrieveByEmailAndWeekEnding(String email, LocalDate weekEnding);
	TimesheetEntity create(Transformer<TimesheetEntity> transformer);
	Collection<TimesheetEntity> retrieveDrafts(DefaultOidcUser user);
}
