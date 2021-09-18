package com.doubao.statemachine.config.configurers.transition;

/**
 * EntranceTransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 21:30
 */
public interface EntranceTransitionConfigurer<S, E, C> {
	/**
	 * specify target state for this transition
	 *
	 * @param target target state
	 * @return configurer
	 */
	EntranceTransitionConfigurer<S, E, C> target(S target);
}
