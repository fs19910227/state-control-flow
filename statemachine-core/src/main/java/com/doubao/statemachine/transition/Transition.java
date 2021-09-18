package com.doubao.statemachine.transition;

import java.util.List;

import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.guard.Guard;
import com.doubao.statemachine.state.State;

/**
 * @author doubao
 * @date 2021/4/28
 */
public interface Transition<S, E, C> {

	/**
	 * Transit this transition with a give state context.
	 *
	 * @param context the state context
	 * @return target state
	 */
	boolean transit(StateContext<S, E, C> context);

	/**
	 * Gets the source state of this transition.
	 *
	 * @return the source state
	 */
	State<S, E, C> getSource();

	/**
	 * Gets the target state of this transition.
	 *
	 * @return the target state
	 */
	State<S, E, C> getTarget();

	/**
	 * Gets the guard of this transition.
	 *
	 * @return the guard
	 */
	Guard<S, E, C> getGuard();

	/**
	 * Gets the transition actions.
	 *
	 * @return the transition actions
	 */
	List<Action<S, E, C>> getActions();

	/**
	 * Gets the transition trigger event.
	 *
	 * @return the trigger event
	 */
	E getEvent();

	/**
	 * Gets the transition kind.
	 *
	 * @return the transition kind
	 */
	TransitionKind getKind();
}
