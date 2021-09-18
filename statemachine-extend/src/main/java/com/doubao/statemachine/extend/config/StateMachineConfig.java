package com.doubao.statemachine.extend.config;

import com.doubao.statemachine.Priority;
import com.doubao.statemachine.config.builders.StateMachineConfigurationConfigurer;
import com.doubao.statemachine.config.builders.StateMachineStateConfigurer;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;

/**
 * indicate a class as a StateMachine Config class
 *
 * @author doubao
 * @date 2021/4/30
 */
public interface StateMachineConfig<S, E, C> extends Priority {
	String DEFAULT_GROUP = "DEFAULT";

	/**
	 * config states
	 *
	 * @param stateConfigurer
	 */
	void configureStates(StateMachineStateConfigurer<S, E, C> stateConfigurer);

	/**
	 * config configuration
	 *
	 * @param configConfigurer configurer
	 */
	void configureConfiguration(StateMachineConfigurationConfigurer<S, E, C> configConfigurer);

	/**
	 * config transitions
	 *
	 * @param transitionConfigurer
	 */
	void configureTransitions(StateMachineTransitionConfigurer<S, E, C> transitionConfigurer);

	/**
	 * is support content
	 *
	 * @param content content
	 * @return
	 */
	boolean support(C content);

}
