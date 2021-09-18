package com.doubao.statemachine.config.builders;

import java.util.ArrayList;
import java.util.List;

import com.doubao.statemachine.listener.StateMachineListener;
import com.doubao.statemachine.config.configurers.configuration.ConfigurationConfigurer;
import com.doubao.statemachine.config.configurers.configuration.DefaultConfigurationConfigurer;
import com.doubao.statemachine.config.configurers.configuration.DefaultVerifierConfigurer;
import com.doubao.statemachine.config.configurers.configuration.VerifierConfigurer;
import com.doubao.statemachine.config.model.ConfigurationData;
import com.doubao.statemachine.config.model.VerifierData;
import com.doubao.statemachine.content.ContentStateChanger;
import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.interceptor.InternalStateMachineInterceptor;
import com.doubao.statemachine.interceptor.StateMachineInterceptor;

/**
 * @author doubao
 * @date 2021/4/28
 */
public class StateMachineConfigBuilder<S, E, C> extends Builder<ConfigurationData<S, E, C>>
	implements StateMachineConfigurationConfigurer<S, E, C> {
	private String machineId;
	private StateMachine<S, E, C> parent;
	private VerifierData<S, E, C> verifierData;
	private List<StateMachineListener<S, E, C>> stateMachineListeners;
	private final List<StateMachineInterceptor<S, E, C>> interceptors = new ArrayList<>();
	private ContentStateChanger<S, C> contentStateChanger;
	private List<StateMachineInterceptor<S, E, C>> customInterceptors;
	private boolean inherit;

	@Override
	public ConfigurationData<S, E, C> build() {
		super.doConfig();
		ConfigurationData<S, E, C> configurationData = new ConfigurationData<>();
		InternalStateMachineInterceptor<S, E, C> internal = new InternalStateMachineInterceptor<>();
		internal.setContentStateChanger(contentStateChanger);
		interceptors.add(internal);
		if (customInterceptors != null) {
			interceptors.addAll(customInterceptors);
		}
		//default close verifier
		if (verifierData == null) {
			verifierData = new VerifierData<>();
			verifierData.setEnabled(false);
		}
		return configurationData
			.setParent(parent)
			.setInheritFromParent(inherit)
			.setMachineId(machineId)
			.setVerifierData(verifierData)
			.setListeners(stateMachineListeners)
			.setInterceptors(interceptors);
	}

	@Override
	public ConfigurationConfigurer<S, E, C> withConfiguration() {
		return apply(new DefaultConfigurationConfigurer<>());
	}

	@Override
	public VerifierConfigurer<S, E, C> withVerifier() {
		return apply(new DefaultVerifierConfigurer<>());
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public void setParent(StateMachine<S, E, C> parent) {
		this.parent = parent;
	}

	public void setVerifierData(VerifierData<S, E, C> verifierData) {
		this.verifierData = verifierData;
	}

	public void setStateMachineListeners(
		List<StateMachineListener<S, E, C>> stateMachineListeners) {
		this.stateMachineListeners = stateMachineListeners;
	}

	public void setContentStateChanger(ContentStateChanger<S, C> contentStateChanger) {
		this.contentStateChanger = contentStateChanger;
	}

	public void setStateMachineInterceptors(List<StateMachineInterceptor<S, E, C>> interceptors) {
		this.customInterceptors = interceptors;
	}

	public void setInheritFromParent(boolean inherit) {
		this.inherit = inherit;
	}
}
