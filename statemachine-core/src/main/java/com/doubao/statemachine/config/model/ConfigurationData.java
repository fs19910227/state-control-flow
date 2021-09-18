package com.doubao.statemachine.config.model;

import java.util.List;

import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.listener.StateMachineListener;
import com.doubao.statemachine.interceptor.StateMachineInterceptor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author doubao
 * @date 2021/4/28
 */
@Data
@Accessors(chain = true)
public class ConfigurationData<S, E, C> {
	/**
	 * machine id
	 */
	private String machineId;
	/**
	 * machine parent
	 */
	private StateMachine<S, E, C> parent;
	/**
	 * whether inherit configs from parent
	 */
	private boolean inheritFromParent;
	/**
	 * verifier configs
	 */
	private VerifierData<S, E, C> verifierData;
	/**
	 * listeners
	 */
	private List<StateMachineListener<S, E, C>> listeners;
	/**
	 * interceptors
	 */
	private List<StateMachineInterceptor<S, E, C>> interceptors;
}
