package com.doubao.statemachine.config.builders;

import com.doubao.statemachine.config.configurers.configuration.ConfigurationConfigurer;
import com.doubao.statemachine.config.configurers.configuration.VerifierConfigurer;

/**
 * @author doubao
 * @date 2021/4/28
 */
public interface StateMachineConfigurationConfigurer<S, E, C> {

	/**
	 * Gets a configurer for generic config.
	 *
	 * @return {@link ConfigurationConfigurer} for chaining
	 * @throws Exception if configuration error happens
	 */
	ConfigurationConfigurer<S, E, C> withConfiguration() ;

	/**
	 * Gets a configurer for state machine model verifier.
	 *
	 * @return {@link VerifierConfigurer} for chaining
	 * @throws Exception if configuration error happens
	 */
	VerifierConfigurer<S, E, C> withVerifier() ;

}
