package com.doubao.statemachine.config.configurers.configuration;

import java.util.ArrayList;
import java.util.List;

import com.doubao.statemachine.config.builders.StateMachineConfigBuilder;
import com.doubao.statemachine.config.configurers.ConfigurerAdapter;
import com.doubao.statemachine.content.ContentStateChanger;
import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.listener.StateMachineListener;
import com.doubao.statemachine.config.model.ConfigurationData;
import com.doubao.statemachine.interceptor.StateMachineInterceptor;

/**
 * @author doubao
 * @date 2021/4/28
 */
public class DefaultConfigurationConfigurer<S, E, C>
	extends ConfigurerAdapter<ConfigurationData<S, E, C>, StateMachineConfigBuilder<S, E, C>>
	implements ConfigurationConfigurer<S, E, C> {
	private String machineId;
	private StateMachine<S, E, C> parent;
	private ContentStateChanger<S, C> contentStateChanger;
	private final List<StateMachineListener<S, E, C>> stateMachineListeners = new ArrayList<>();
	private final List<StateMachineInterceptor<S, E, C>> interceptors = new ArrayList<>();
	private boolean inherit=true;

	@Override
	public void configure(StateMachineConfigBuilder<S, E, C> builder) {
		builder.setMachineId(machineId);
		builder.setParent(parent);
		builder.setInheritFromParent(inherit);
		builder.setStateMachineListeners(stateMachineListeners);
		builder.setContentStateChanger(contentStateChanger);
		builder.setStateMachineInterceptors(interceptors);
	}

	@Override
	public ConfigurationConfigurer<S, E, C> machineId(String id) {
		machineId = id;
		return this;
	}

	@Override
	public ConfigurationConfigurer<S, E, C> parent(StateMachine<S, E, C> parent) {
		this.parent = parent;
		return this;
	}

	@Override
	public ConfigurationConfigurer<S, E, C> inheritFromParent(boolean inherit) {
		this.inherit = inherit;
		return this;
	}

	@Override
	public ConfigurationConfigurer<S, E, C> listener(StateMachineListener<S, E, C> listener) {
		stateMachineListeners.add(listener);
		return this;
	}

	@Override
	public ConfigurationConfigurer<S, E, C> interceptor(StateMachineInterceptor<S, E, C> interceptor) {
		interceptors.add(interceptor);
		return this;
	}

	@Override
	public ConfigurationConfigurer<S, E, C> contentStateChanger(ContentStateChanger<S, C> contentStateChanger) {
		this.contentStateChanger = contentStateChanger;
		return this;
	}
}
