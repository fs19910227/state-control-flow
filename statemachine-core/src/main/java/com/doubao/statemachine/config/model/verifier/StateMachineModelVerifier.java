package com.doubao.statemachine.config.model.verifier;

import com.doubao.statemachine.config.model.StateMachineModel;

public interface StateMachineModelVerifier<S, E, C> {

	/**
	 * Verify a state machine model.
	 *
	 * @param model the state machine model
	 */
	void verify(StateMachineModel<S, E, C> model);
}