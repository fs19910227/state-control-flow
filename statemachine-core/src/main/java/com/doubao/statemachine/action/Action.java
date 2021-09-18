package com.doubao.statemachine.action;

import com.doubao.statemachine.Priority;
import com.doubao.statemachine.StateContext;

/**
 * action will be performed after a state transition
 *
 * @author doubao
 * @date 2021/04/27 22:01
 */
@FunctionalInterface
public interface Action<S, E, C> extends Priority {
	/**
	 * execute before perform action
	 * default do nothing
	 * @param context context
	 */
	default void beforeAction(StateContext<S,E,C> context){

	}
	/**
	 * perform action
	 *
	 * @param context context
	 */
	void evaluate(StateContext<S, E, C> context);
	/**
	 * execute after perform action
	 * default do nothing
	 * @param context context
	 */
	default void afterAction(StateContext<S,E,C> context){

	}

	/**
	 * execute when exception happened
	 * @param context context
	 * @param exception exception
	 */
	default void onException(StateContext<S,E,C> context,Throwable exception) {
		throw new RuntimeException(exception);
	}
}
