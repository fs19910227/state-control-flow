package com.doubao.statemachine.core;

import com.doubao.statemachine.config.model.TransitionsData;
import com.doubao.statemachine.config.builders.StateMachineConfigBuilder;
import com.doubao.statemachine.config.builders.StateMachineConfigurationConfigurer;
import com.doubao.statemachine.config.builders.StateMachineStateBuilder;
import com.doubao.statemachine.config.builders.StateMachineTransitionBuilder;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.doubao.statemachine.config.model.ConfigurationData;
import com.doubao.statemachine.config.model.StateMachineModel;
import com.doubao.statemachine.config.model.StatesData;

/**
 * StateMachineBuilder
 *
 * @author doubao
 * @date 2021/04/27 23:12
 */
public class StateMachineBuilder {

	public static <S, E, C> Builder<S, E, C> builder() {
		return new Builder<>();
	}

	public static class Builder<S, E, C> {
		private final StateMachineTransitionBuilder<S, E, C> transitionBuilder = new StateMachineTransitionBuilder<>();
		private final StateMachineConfigBuilder<S, E, C> configBuilder = new StateMachineConfigBuilder<>();
		private final StateMachineStateBuilder<S, E, C> stateBuilder = new StateMachineStateBuilder<>();

		public StateMachineTransitionConfigurer<S, E, C> configureTransitions() {
			return transitionBuilder;
		}

		public StateMachineConfigurationConfigurer<S, E, C> configureConfigurations() {
			return configBuilder;
		}

		public StateMachineStateBuilder<S, E, C> configureStates() {
			return stateBuilder;
		}

		public StateMachine<S, E, C> build() {
			StateMachineModel<S, E, C> secStateMachineModel = buildModel();
			return StateMachineFactory.buildStateMachine(secStateMachineModel);
		}
		public StateMachineModel<S, E, C> buildModel() {
			TransitionsData<S, E, C> transitionsData = transitionBuilder.build();
			ConfigurationData<S, E, C> configurationData = configBuilder.build();
			StatesData<S, E, C> statesData = stateBuilder.build();
			return new StateMachineModel<>(configurationData,
				statesData, transitionsData);
		}

	}

}
