package com.doubao.statemachine.listener;

import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.transition.Transition;
import com.doubao.statemachine.state.State;

/**
 * StateMachineListener Adapter
 * use StateMachineListener with default method directly
 *
 * @author doubao
 * @date 2021/4/29
 */
@Deprecated
public class StateMachineListenerAdapter<S, E, C> implements StateMachineListener<S, E, C> {

	@Override
	public void stateChanged(E event, State<S, E, C> from, State<S, E, C> to, C content) {

	}

	@Override
	public void transitionStarted(Transition<S, E, C> transition,
                                  Message<S, E, C> message) {

	}

	@Override
	public void transitionSucceed(Transition<S, E, C> transition,
		Message<S, E, C> message) {

	}

	@Override
	public void eventNotAccepted(Message<S, E, C> message) {

	}

	@Override
	public void transitionNotFound(Message<S, E, C> message) {

	}
}
