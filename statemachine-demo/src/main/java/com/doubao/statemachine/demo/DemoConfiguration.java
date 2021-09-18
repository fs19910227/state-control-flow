package com.doubao.statemachine.demo;

import java.util.LinkedHashMap;
import java.util.Map;

import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.States;
import com.google.common.collect.Lists;
import com.doubao.statemachine.demo.listener.AutoSaveListener;
import com.doubao.statemachine.extend.config.StateMachineGlobalConfig;
import com.doubao.statemachine.extend.support.DefaultEventTranslator;
import com.doubao.statemachine.extend.support.DefaultStateTranslator;
import com.doubao.statemachine.extend.support.translator.EventTranslator;
import com.doubao.statemachine.extend.support.StateDesc;
import com.doubao.statemachine.extend.support.translator.StateTranslator;
import com.doubao.statemachine.extend.support.uml.EventViewMode;
import com.doubao.statemachine.extend.support.uml.UmlGeneratorConfigProperties;
import com.doubao.statemachine.spring.annotation.StateMachineScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * demo configuration
 *
 * @author doubao
 * @date 2021/5/14
 */
@Configuration
@StateMachineScan("com.doubao.statemachine.demo.configs")
public class DemoConfiguration {
	@Bean
	public StateMachineGlobalConfig stateMachineGlobalConfig() {
		StateMachineGlobalConfig stateMachineGlobalConfig = new StateMachineGlobalConfig();
		stateMachineGlobalConfig.setGlobalListeners(Lists.newArrayList(new AutoSaveListener()));
		return stateMachineGlobalConfig;
	}

	@Bean
	public UmlGeneratorConfigProperties umlGeneratorConfigProperties() {
		return new UmlGeneratorConfigProperties()
			.setEventViewMode(EventViewMode.NAME_ONLY)
			.setShowInternalTransition(false);
	}

	@Bean
	public StateTranslator<States> stateTranslator() {
		return new TestStateTranslator();
	}

	@Bean
	public EventTranslator<Events> eventTranslator() {
		return new DefaultEventTranslator<>(Events::getDesc);
	}

	public static class TestStateTranslator extends DefaultStateTranslator<States> {

		public TestStateTranslator() {
			super(States::getDesc);
		}

		@Override
		public Map<String, String> getProperties(StateDesc<States> state) {
			States id = state.getId();
			int code = id.getCode();
			Map<String, String> map;
			if (state.getProperties() == null) {
				map = new LinkedHashMap<>();
			} else {
				map = state.getProperties();
			}
			map.put("code", code + "");
			return map;
		}
	}
}
