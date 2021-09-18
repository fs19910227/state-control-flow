package com.doubao.statemachine.interceptor;

import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.state.State;
import com.doubao.statemachine.transition.Transition;

/**
 * StateMachineInterceptorAdapter
 * use StateMachineInterceptor with default method instead
 * @author doubao
 * @date 2021/5/7
 *
 */
@Deprecated
public class StateMachineInterceptorAdapter<S, E, C> implements StateMachineInterceptor<S, E, C> {
	@Override
	public void preAction(State<S, E, C> state, Message<S, E, C> message, Transition<S, E, C> transition,
		StateMachine<S, E, C> stateMachine) {

	}
	@Override
	public void postAction(State<S, E, C> state, Message<S, E, C> message, Transition<S, E, C> transition,
		StateMachine<S, E, C> stateMachine) {

	}
	@Override
	public void postTransition(State<S, E, C> state, Message<S, E, C> message, Transition<S, E, C> transition,
		StateMachine<S, E, C> stateMachine) {

	}

	@Override
	public void preTransition(State<S, E, C> state, Message<S, E, C> message, Transition<S, E, C> transition,
		StateMachine<S, E, C> stateMachine) {

	}
}
