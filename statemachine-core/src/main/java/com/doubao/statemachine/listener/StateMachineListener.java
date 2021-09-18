package com.doubao.statemachine.listener;

import com.doubao.statemachine.Priority;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.state.State;
import com.doubao.statemachine.transition.Transition;

/**
 * StateMachineListener
 *
 * @author doubao
 * @date 2021/4/29
 */
public interface StateMachineListener<S, E, C> extends Priority {
	/**
	 * listen when state changed
	 *
	 * @param event   event
	 * @param from    from state
	 * @param to      to state
	 * @param content content
	 */
	default void stateChanged(E event, State<S, E, C> from, State<S, E, C> to, C content){

	};

	/**
	 * listen when transition start
	 *
	 * @param transition transition
	 * @param message    message
	 */
	default void transitionStarted(Transition<S, E, C> transition, Message<S, E, C> message){

	};

	/**
	 * listen when transition succeed
	 *
	 * @param transition transition
	 * @param message    message
	 */
	default void transitionSucceed(Transition<S, E, C> transition, Message<S, E, C> message){

	};

	/**
	 * listen when cannot find transition
	 *
	 * @param message message
	 */
	default void transitionNotFound(Message<S, E, C> message){

	};

	/**
	 * listen when event not accepted
	 * maybe intercept by a guard
	 *
	 * @param message message
	 */
	default void eventNotAccepted(Message<S, E, C> message){

	};
}
