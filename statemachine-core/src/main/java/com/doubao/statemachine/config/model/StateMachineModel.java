package com.doubao.statemachine.config.model;

import lombok.Data;

/**
 * state machine model
 *
 * @author doubao
 * @date 2021/4/28
 */
@Data
public class StateMachineModel<S, E, C> {
	private final ConfigurationData<S, E, C> configurationData;
	private final StatesData<S, E, C> statesData;
	private final TransitionsData<S, E, C> transitionsData;
	private StateMachineModel<S,E,C> child;

	public StateMachineModel(ConfigurationData<S, E, C> configurationData,
		StatesData<S, E, C> statesData,
		TransitionsData<S, E, C> transitionsData) {
		this.configurationData = configurationData;
		this.statesData = statesData;
		this.transitionsData = transitionsData;
	}
}
