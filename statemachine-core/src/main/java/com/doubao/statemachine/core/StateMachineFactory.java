package com.doubao.statemachine.core;

import com.doubao.statemachine.util.StateMachineUtil;
import com.doubao.statemachine.config.model.ConfigurationData;
import com.doubao.statemachine.config.model.StateMachineModel;
import com.doubao.statemachine.config.model.VerifierData;
import com.doubao.statemachine.config.model.verifier.StateMachineModelVerifier;
import lombok.experimental.UtilityClass;

/**
 * StateMachineFactory
 * use model build stateMachine
 *
 * @author doubao
 * @date 2021/4/28
 */
@UtilityClass
public class StateMachineFactory {
	/**
	 * build stateMachine fromModel
	 *
	 * @param stateMachineModel stateMachine
	 * @return StateMachine
	 */
	public <S, E, C> StateMachine<S, E, C> buildStateMachine(StateMachineModel<S, E, C> stateMachineModel) {
		StateMachineModelParser<S, E, C> modelParser = new StateMachineModelParser<>(stateMachineModel);
		modelParser.doParse();

		ConfigurationData<S, E, C> configurationData = stateMachineModel.getConfigurationData();
		//verifier
		VerifierData<S, E, C> verifierData = configurationData.getVerifierData();
		if (verifierData.isEnabled()) {
			for (StateMachineModelVerifier<S, E, C> verifier : verifierData.getVerifierList()) {
				verifier.verify(stateMachineModel);
			}
		}
		//create machine,must have id and model
		String machineId = configurationData.getMachineId() == null ? StateMachineUtil.randomMachineId()
			: configurationData.getMachineId();
		DefaultStateMachine<S, E, C> secDefaultStateMachine = new DefaultStateMachine<>(machineId,
			stateMachineModel);
		//set config
		secDefaultStateMachine.setParent(configurationData.getParent());
		secDefaultStateMachine.setStateMachineListeners(configurationData.getListeners());
		secDefaultStateMachine.setInterceptors(configurationData.getInterceptors());
		//set states
		secDefaultStateMachine.setStateMap(modelParser.getStatesMap());
		//set transitions
		secDefaultStateMachine.setTransitionMap(modelParser.getTransitionsMap());
		//init
		secDefaultStateMachine.init();
		return secDefaultStateMachine;
	}

}
