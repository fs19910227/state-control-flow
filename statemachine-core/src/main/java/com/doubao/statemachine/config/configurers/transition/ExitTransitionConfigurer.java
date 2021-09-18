package com.doubao.statemachine.config.configurers.transition;

/**
 * ExitTransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 21:30
 */
public interface ExitTransitionConfigurer<S, E, C> {
	/**
	 * specify target state for this transition
	 *
	 * @param target target state
	 * @return configurer
	 */
	ExitTransitionConfigurer<S, E, C> target(S target);
}
