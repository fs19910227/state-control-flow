package com.doubao.statemachine.config.configurers.configuration;

import java.util.ArrayList;
import java.util.List;

import com.doubao.statemachine.config.builders.StateMachineConfigBuilder;
import com.doubao.statemachine.config.configurers.ConfigurerAdapter;
import com.doubao.statemachine.config.model.ConfigurationData;
import com.doubao.statemachine.config.model.VerifierData;
import com.doubao.statemachine.config.model.verifier.StateMachineModelVerifier;

/**
 * @author doubao
 * @date 2021/4/28
 */
public class DefaultVerifierConfigurer<S, E, C>
	extends ConfigurerAdapter<ConfigurationData<S, E, C>, StateMachineConfigBuilder<S, E, C>>
	implements VerifierConfigurer<S, E, C> {
	private boolean enabled;
	private final List<StateMachineModelVerifier<S, E, C>> verifierList = new ArrayList<>();

	@Override
	public void configure(StateMachineConfigBuilder<S, E, C> builder) {
		VerifierData<S, E, C> verifierData = new VerifierData<>();
		verifierData.setVerifierList(verifierList)
			.setEnabled(enabled);
		builder.setVerifierData(verifierData);

	}

	@Override
	public VerifierConfigurer<S, E, C> enabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	@Override
	public VerifierConfigurer<S, E, C> verifier(StateMachineModelVerifier<S, E, C> verifier) {
		verifierList.add(verifier);
		return this;
	}
}
