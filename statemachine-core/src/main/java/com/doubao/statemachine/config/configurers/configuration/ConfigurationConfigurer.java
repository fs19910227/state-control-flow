package com.doubao.statemachine.config.configurers.configuration;

import com.doubao.statemachine.content.ContentStateChanger;
import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.listener.StateMachineListener;
import com.doubao.statemachine.interceptor.StateMachineInterceptor;

/**
 * ConfigurationConfigurer
 *
 * @author doubao
 * @date 2021/4/28
 */
public interface ConfigurationConfigurer<S, E, C> {
	/**
	 * config state machine id
	 *
	 * @param id id
	 * @return ConfigurationConfigurer
	 */
	ConfigurationConfigurer<S, E, C> machineId(String id);

	/**
	 * config stateMachine parent
	 *
	 * @param parent parent
	 * @return ConfigurationConfigurer
	 */
	ConfigurationConfigurer<S, E, C> parent(StateMachine<S, E, C> parent);



	/**
	 * will automatic change content state when state changed
	 *
	 * @param contentStateChanger contentStateChanger
	 * @return
	 */
	ConfigurationConfigurer<S, E, C> contentStateChanger(ContentStateChanger<S, C> contentStateChanger);

	/**
	 * Specify a Listener for interceptor
	 *
	 * @param interceptor
	 * @return
	 */
	ConfigurationConfigurer<S, E, C> interceptor(StateMachineInterceptor<S, E, C> interceptor);

	/**
	 * config whether inherit configs from parent machine
	 * not work if don't have a parent
	 * default true
	 * @param inherit inherit
	 * @return this
	 */
	ConfigurationConfigurer<S, E, C> inheritFromParent(boolean inherit);

	/**
	 * Specify a Listener for StateMachine
	 *
	 * @param listener listener
	 * @return this
	 */
	ConfigurationConfigurer<S, E, C> listener(StateMachineListener<S, E, C> listener);

}
