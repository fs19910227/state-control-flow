package com.doubao.statemachine.config.configurers.transition;

import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.guard.Guard;

/**
 * TransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 21:30
 */
public interface NormalTransitionConfigurer<T, S, E, C> extends TransitionConfigurer<T, S, E> {
	/**
	 * specify transition action
	 *
	 * @param action action
	 * @return configurer
	 */
	T action(Action<S, E, C> action);

	/**
	 * specify transition condition guard
	 *
	 * @param guard guard
	 * @return configurer
	 */
	T guard(Guard<S, E, C> guard);
}
