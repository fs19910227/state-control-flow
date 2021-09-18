package com.doubao.statemachine.config.configurers.transition;

/**
 * SourceItemConfigurer
 *
 * @author doubao
 * @date 2021/04/27 21:30
 */
public interface SourceItemConfigurer<T, S, E> {
	/**
	 * specify source state for this transition
	 *
	 * @param source source state
	 * @return configurer
	 */
	T source(S source);

}
