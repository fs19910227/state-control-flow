package com.doubao.statemachine.config.configurers.transition.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.doubao.statemachine.config.builders.StateMachineTransitionBuilder;
import com.doubao.statemachine.config.model.TransitionData;
import com.doubao.statemachine.transition.TransitionKind;
import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.config.configurers.transition.AbstractTransitionConfigurer;
import com.doubao.statemachine.guard.Guard;

/**
 * simple SourceBaseTransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 22:18
 */
public class DefaultSourceBaseTransitionConfigurer<S, E, C> extends AbstractTransitionConfigurer<S, E, C> implements
	SourceBaseTransitionConfigurer<S, E, C> {
	private final S source;
	private final List<TransitionData<S, E, C>> transitionDataList;

	public DefaultSourceBaseTransitionConfigurer(S source) {
		this.source = source;
		this.transitionDataList = new ArrayList<>();
	}

	@Override
	public void configure(StateMachineTransitionBuilder<S, E, C> builder) {
		for (TransitionData<S, E, C> secTransitionData : transitionDataList) {
			builder.addTransition(secTransitionData);
		}
	}

	@Override
	public SourceBaseTransitionConfigurer<S, E, C> external(E event, S target) {
		return external(event, target, null, Collections.emptyList());
	}

	@Override
	public SourceBaseTransitionConfigurer<S, E, C> external(E event, S target, Guard<S, E, C> guard) {
		return external(event, target, guard, Collections.emptyList());
	}

	@Override
	public SourceBaseTransitionConfigurer<S, E, C> external(E event, S target, Action<S, E, C> action) {
		return external(event, target, null, action);
	}

	@Override
	public SourceBaseTransitionConfigurer<S, E, C> external(E event, S target, List<Action<S, E, C>> actions) {
		return external(event, target, null, actions);
	}

	@Override
	public SourceBaseTransitionConfigurer<S, E, C> external(E event, S target, Guard<S, E, C> guard,
		Action<S, E, C> action) {
		return external(event, target, guard, action == null ? null : Collections.singletonList(action));
	}

	@Override
	public SourceBaseTransitionConfigurer<S, E, C> external(E event, S target, Guard<S, E, C> guard,
		List<Action<S, E, C>> actions) {
		TransitionData<S, E, C> secTransitionData = new TransitionData<S, E, C>()
			.setSource(source)
			.setTarget(target)
			.setEvent(event)
			.setGuard(guard)
			.setKind(TransitionKind.EXTERNAL)
			.setActions(actions);
		transitionDataList.add(secTransitionData);
		return this;
	}

	@Override
	public SourceBaseTransitionConfigurer<S, E, C> internal(E event, Action<S, E, C> action) {
		return internal(event, null, action);
	}

	@Override
	public SourceBaseTransitionConfigurer<S, E, C> internal(E event, Guard<S, E, C> guard, Action<S, E, C> action) {
		return internal(event, guard, action == null ? null : Collections.singletonList(action));
	}

	@Override
	public SourceBaseTransitionConfigurer<S, E, C> internal(E event, List<Action<S, E, C>> actions) {
		return internal(event, null, actions);
	}

	@Override
	public SourceBaseTransitionConfigurer<S, E, C> internal(E event, Guard<S, E, C> guard,
		List<Action<S, E, C>> actions) {
		TransitionData<S, E, C> secTransitionData = new TransitionData<S, E, C>()
			.setSource(source)
			.setEvent(event)
			.setGuard(guard)
			.setKind(TransitionKind.INTERNAL)
			.setActions(actions);
		transitionDataList.add(secTransitionData);
		return this;
	}
}
