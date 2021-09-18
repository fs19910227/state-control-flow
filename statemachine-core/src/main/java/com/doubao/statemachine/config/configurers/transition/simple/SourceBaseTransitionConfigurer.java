package com.doubao.statemachine.config.configurers.transition.simple;

import java.util.List;

import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.guard.Guard;

/**
 * SourceBaseTransitionConfigurer
 *
 * @author doubao
 * @date 2021/05/04 21:20
 */
public interface SourceBaseTransitionConfigurer<S, E, C> {

	/**
	 * external transition config
	 *
	 * @param event  event
	 * @param target target state
	 * @return this
	 */
	SourceBaseTransitionConfigurer<S, E, C> external(E event, S target);

	/**
	 * external transition config
	 *
	 * @param event  event
	 * @param target target state
	 * @param guard  guard
	 * @return this
	 */
	SourceBaseTransitionConfigurer<S, E, C> external(E event, S target, Guard<S, E, C> guard);

	/**
	 * external transition config
	 *
	 * @param event  event
	 * @param target target state
	 * @param action action
	 * @return this
	 */
	SourceBaseTransitionConfigurer<S, E, C> external(E event, S target, Action<S, E, C> action);

	/**
	 * external transition config
	 *
	 * @param event  event
	 * @param target target state
	 * @param actions actions
	 * @return this
	 */
	SourceBaseTransitionConfigurer<S, E, C> external(E event, S target, List<Action<S, E, C>> actions);

	/**
	 * external transition config
	 *
	 * @param event  event
	 * @param target target state
	 * @param guard  guard
	 * @param action action
	 * @return this
	 */
	SourceBaseTransitionConfigurer<S, E, C> external(E event, S target, Guard<S, E, C> guard,
		Action<S, E, C> action);
	/**
	 * external transition config
	 *
	 * @param event  event
	 * @param target target state
	 * @param guard  guard
	 * @param actions actions
	 * @return this
	 */
	SourceBaseTransitionConfigurer<S, E, C> external(E event, S target, Guard<S, E, C> guard,
		List<Action<S, E, C>> actions);

	/**
	 * internal transition config
	 *
	 * @param event  event
	 * @param action actions
	 * @return this
	 */
	SourceBaseTransitionConfigurer<S, E, C> internal(E event, Action<S, E, C> action);

	/**
	 * internal transition config
	 *
	 * @param event   event
	 * @param actions actions
	 * @return this
	 */
	SourceBaseTransitionConfigurer<S, E, C> internal(E event, List<Action<S, E, C>> actions);

	/**
	 * internal transition config
	 *
	 * @param event   event
	 * @param guard   guard
	 * @param actions actions
	 * @return this
	 */
	SourceBaseTransitionConfigurer<S, E, C> internal(E event, Guard<S, E, C> guard, List<Action<S, E, C>> actions);

	/**
	 * internal transition config
	 *
	 * @param event  event
	 * @param guard  guard
	 * @param action action
	 * @return this
	 */
	SourceBaseTransitionConfigurer<S, E, C> internal(E event, Guard<S, E, C> guard,
		Action<S, E, C> action);
}
