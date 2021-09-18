package com.doubao.statemachine.config.configurers.configuration;

import com.doubao.statemachine.config.model.verifier.StateMachineModelVerifier;

/**
 * @author doubao
 * @date 2021/4/28
 */
public interface VerifierConfigurer<S, E, C> {

	/**
	 * Specify if verifier is enabled. On default verifier is enabled.
	 *
	 * @param enabled the enable flag
	 * @return configurer for chaining
	 */
	VerifierConfigurer<S, E, C> enabled(boolean enabled);

	/**
	 * Specify a custom model verifier.
	 *
	 * @param verifier the state machine model verifier
	 * @return configurer for chaining
	 */
	VerifierConfigurer<S, E, C> verifier(StateMachineModelVerifier<S, E, C> verifier);
}
