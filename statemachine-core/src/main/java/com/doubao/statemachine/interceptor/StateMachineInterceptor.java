package com.doubao.statemachine.interceptor;

import com.doubao.statemachine.Priority;
import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.transition.Transition;
import com.doubao.statemachine.state.State;

/**
 * StateMachineInterceptor
 *
 * @author doubao
 * @date 2021/4/29
 */
public interface StateMachineInterceptor<S, E, C> extends Priority {
	/**
	 * pre process before guard perform
	 * if don't have any guard,won't process
	 *
	 * @param state        current state
	 * @param message      message
	 * @param transition   transition
	 * @param stateMachine stateMachine
	 */
	default void preGuard(State<S, E, C> state, Message<S, E, C> message, Transition<S, E, C> transition,
                          StateMachine<S, E, C> stateMachine){

	};
	/**
	 * pre process before action perform
	 * if don't have any action,won't process
	 *
	 * @param state        current state
	 * @param message      message
	 * @param transition   transition
	 * @param stateMachine stateMachine
	 */
	default void preAction(State<S, E, C> state, Message<S, E, C> message, Transition<S, E, C> transition,
		StateMachine<S, E, C> stateMachine){

	};
	/**
	 * post process action action perform
	 * if don't have any action,won't process
	 *
	 * @param state        current state
	 * @param message      message
	 * @param transition   transition
	 * @param stateMachine stateMachine
	 */
	default void postAction(State<S, E, C> state, Message<S, E, C> message, Transition<S, E, C> transition,
		StateMachine<S, E, C> stateMachine){

	};
	/**
	 * post process after transit
	 * happen after actions and before listener
	 *
	 * @param state        current state
	 * @param message      message
	 * @param transition   transition
	 * @param stateMachine stateMachine
	 */
	default void postTransition(State<S, E, C> state, Message<S, E, C> message, Transition<S, E, C> transition,
		StateMachine<S, E, C> stateMachine){

	};

	/**
	 * pre process before transit
	 * happen before transition
	 *
	 * @param state        current state
	 * @param message      message
	 * @param transition   transition
	 * @param stateMachine stateMachine
	 */
	default void preTransition(State<S, E, C> state, Message<S, E, C> message, Transition<S, E, C> transition,
		StateMachine<S, E, C> stateMachine){

	};
}
