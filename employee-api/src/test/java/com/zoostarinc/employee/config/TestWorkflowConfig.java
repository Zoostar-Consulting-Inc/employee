package com.zoostarinc.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import net.zoostar.common.core.workflow.State;
import net.zoostar.common.core.workflow.timesheet.state.StateNew;

@Configuration
class TestWorkflowConfig {

	static final SimpleModule SIMPLE_STATE_MODULE = new SimpleModule().addAbstractTypeMapping(State.class, StateNew.class);
	
	@Bean
	ObjectMapper om() {
		var bean = new ObjectMapper();
		bean.registerModule(new Jdk8Module()).registerModule(SIMPLE_STATE_MODULE);
		bean.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return bean;
	}
}
