package com.doubao.statemachine.config.configurers.transition;

import com.doubao.statemachine.config.builders.StateMachineTransitionBuilder;
import com.doubao.statemachine.config.configurers.ConfigurerAdapter;
import com.doubao.statemachine.config.model.TransitionsData;

/**
 * abstract TransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 22:17
 */
public abstract class AbstractTransitionConfigurer<S, E, C>
	extends ConfigurerAdapter<TransitionsData<S, E, C>, StateMachineTransitionBuilder<S, E, C>> {
	protected S source;
	protected E event;
}
