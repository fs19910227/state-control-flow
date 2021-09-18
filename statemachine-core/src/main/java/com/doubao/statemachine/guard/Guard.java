package com.doubao.statemachine.guard;

import com.doubao.statemachine.StateContext;

/**
 * Guard is the condition weather the action be executed or not
 *
 * @author doubao
 * @date 2021/04/27 22:06
 */
@FunctionalInterface
public interface Guard<S, E, C> {
	/**
	 * guard condition judge
	 *
	 * @param context context
	 * @return true/false
	 */
	boolean evaluate(StateContext<S, E, C> context);

}
