package com.doubao.statemachine.config.configurers.transition;

/**
 * InternalTransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 21:30
 */
public interface InternalTransitionConfigurer<S, E, C>
	extends NormalTransitionConfigurer<InternalTransitionConfigurer<S, E, C>, S, E, C> {
}
