package com.doubao.statemachine.config.configurers.transition;

import com.doubao.statemachine.config.builders.StateMachineTransitionBuilder;
import com.doubao.statemachine.config.model.TransitionData;
import com.doubao.statemachine.transition.TransitionKind;

/**
 * default EntranceTransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 22:18
 */
public class DefaultEntranceTransitionConfigurer<S, E, C> extends AbstractTransitionConfigurer<S, E, C>
	implements EntranceTransitionConfigurer<S, E, C> {
	private S target;

	public DefaultEntranceTransitionConfigurer(S source) {
		this.source = source;
	}

	@Override
	public void configure(StateMachineTransitionBuilder<S, E, C> builder) {
		TransitionData<S, E, C> data = new TransitionData<>();
		data.setSource(source)
			.setTarget(target)
			.setKind(TransitionKind.EXTERNAL);
		builder.addTransition(data);
	}

	@Override
	public EntranceTransitionConfigurer<S, E, C> target(S target) {
		this.target = target;
		return this;
	}


}
