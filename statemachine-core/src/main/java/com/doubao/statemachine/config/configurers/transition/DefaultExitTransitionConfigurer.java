package com.doubao.statemachine.config.configurers.transition;

import com.doubao.statemachine.config.builders.StateMachineTransitionBuilder;
import com.doubao.statemachine.config.model.TransitionData;
import com.doubao.statemachine.transition.TransitionKind;

/**
 * default ExitTransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 22:18
 */
public class DefaultExitTransitionConfigurer<S, E, C> extends AbstractTransitionConfigurer<S, E, C>
	implements ExitTransitionConfigurer<S, E, C> {
	private S target;

	public DefaultExitTransitionConfigurer(S source) {
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
	public ExitTransitionConfigurer<S, E, C> target(S target) {
		this.target = target;
		return this;
	}


}
