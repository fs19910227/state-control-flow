package com.doubao.statemachine.config.configurers.transition;

import java.util.ArrayList;

import com.doubao.statemachine.config.builders.StateMachineTransitionBuilder;
import com.doubao.statemachine.config.model.TransitionData;
import com.doubao.statemachine.guard.Guard;
import com.doubao.statemachine.transition.TransitionKind;
import com.doubao.statemachine.action.Action;

/**
 * default InternalTransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 22:18
 */
public class DefaultInternalTransitionConfigurer<S, E, C> extends AbstractNormalTransitionConfigurer<S, E, C>
	implements InternalTransitionConfigurer<S, E, C> {

	@Override
	public void configure(StateMachineTransitionBuilder<S, E, C> builder) {
		TransitionData<S, E, C> data = new TransitionData<>();
		data.setSource(source)
			.setEvent(event)
			.setGuard(guard)
			.setKind(TransitionKind.INTERNAL)
			.setActions(actions);
		builder.addTransition(data);
	}


	@Override
	public InternalTransitionConfigurer<S, E, C> action(Action<S, E, C> action) {
		if (this.actions == null) {
			this.actions = new ArrayList<>();
		}
		this.actions.add(action);
		return this;
	}

	@Override
	public InternalTransitionConfigurer<S, E, C> guard(Guard<S, E, C> guard) {
		this.guard = guard;
		return this;
	}

	@Override
	public InternalTransitionConfigurer<S, E, C> source(S source) {
		this.source = source;
		return this;
	}

	@Override
	public InternalTransitionConfigurer<S, E, C> event(E event) {
		this.event = event;
		return this;
	}

}
