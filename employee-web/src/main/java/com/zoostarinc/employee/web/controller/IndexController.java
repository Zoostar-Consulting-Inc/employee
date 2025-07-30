package com.zoostarinc.employee.web.controller;

import java.time.OffsetDateTime;
import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.zoostarinc.employee.service.EmployeeService;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.Utils;
import net.zoostar.common.audit.Timeable;

/**
 * Main Controller class
 */
@Slf4j
@Getter
@Controller
@RequiredArgsConstructor
public class IndexController implements ApplicationContextAware {

	@Value("${build.name}")
	protected String buildName;

	@Value("${build.timestamp}")
	protected String buildTimestamp;

	@Value("${build.version}")
	protected String buildVersion;

	protected ApplicationContext applicationContext;
	
	protected final EmployeeService employeeManager;

	/**
	 * 
	 * @param name  the message to be displayed
	 * @param model
	 * @return greeting message
	 */
	@Timeable
	@GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
	public String greeting(@AuthenticationPrincipal DefaultOidcUser user, Model model, HttpSession session) {
		String homepage = "index";
		log.debug("Loading greeting in env: {}",
				Arrays.toString(applicationContext.getEnvironment().getActiveProfiles()));
		log.debug("Session ID: {}", session.getId());

		log.info("Greet: {}", user.getGivenName());
		model.addAttribute("name", user.getGivenName());
		model.addAttribute("currentTime", OffsetDateTime.now().format(Utils.ISO_DATE_TIME_FORMAT_UPTO_SECONDS));
		model.addAttribute("buildName", buildName);
		model.addAttribute("buildTimestamp", buildTimestamp);
		model.addAttribute("buildVersion", buildVersion);
		
		try {
			var entity = employeeManager.createIfNotFound(user);
			log.info("Nice to meet you {}!", entity.getFirstName());
		} catch (DuplicateKeyException e) {
			log.info("Welcome back {}!", user.getGivenName());
		}

		return homepage;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
