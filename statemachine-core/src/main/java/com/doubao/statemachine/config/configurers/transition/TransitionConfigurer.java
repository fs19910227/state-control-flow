package com.doubao.statemachine.config.configurers.transition;

/**
 * TransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 21:30
 */
public interface TransitionConfigurer<T, S, E> extends SourceItemConfigurer<T,S,E>{

	/**
	 * specify trigger event for this transition
	 *
	 * @param event event
	 * @return configurer
	 */
	T event(E event);
}
