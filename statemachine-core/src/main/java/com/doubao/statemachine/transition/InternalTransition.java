package com.doubao.statemachine.transition;

import java.util.List;

import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.guard.Guard;
import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.state.State;

/**
 * internal transition
 * @author doubao
 * @date 2021/4/28
 */
public class InternalTransition<S, E, C> extends AbstractTransition<S, E, C> {

	public InternalTransition(State<S, E, C> source, E event,
		Guard<S, E, C> guard,
		List<Action<S, E, C>> actions) {
		super(source, event, guard, actions);
	}

	@Override
	public boolean transit(StateContext<S, E, C> context) {
		eventPublisher.notifyTransitionStart(this, context.getMessage());

		if (!processGuard(context)) {
			return false;
		}
		processActions(context);
		eventPublisher.notifyTransitionSuccess(this, context.getMessage());
		return true;
	}

	@Override
	public State<S, E, C> getTarget() {
		return null;
	}

	@Override
	public TransitionKind getKind() {
		return TransitionKind.INTERNAL;
	}
}
