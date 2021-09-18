package com.doubao.statemachine.config.configurers.transition;

/**
 * ExternalTransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 21:30
 */
public interface ExternalTransitionConfigurer<S, E, C>
	extends NormalTransitionConfigurer<ExternalTransitionConfigurer<S, E, C>, S, E, C> {
	/**
	 * specify target state for this transition
	 *
	 * @param target target state
	 * @return configurer
	 */
	ExternalTransitionConfigurer<S, E, C> target(S target);
}
