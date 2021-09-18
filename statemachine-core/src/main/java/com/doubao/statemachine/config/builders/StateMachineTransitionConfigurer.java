package com.doubao.statemachine.config.builders;

import com.doubao.statemachine.config.configurers.transition.*;
import com.doubao.statemachine.config.configurers.transition.simple.SourceBaseTransitionConfigurer;

/**
 * StateMachineTransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 21:27
 */
public interface StateMachineTransitionConfigurer<S, E, C> {

	/**
	 * get configurer for simple SourceBaseTransitionConfigurer
	 *
	 * @param source source state
	 * @return {@link SourceBaseTransitionConfigurer}
	 */
	SourceBaseTransitionConfigurer<S, E, C> withSource(S source);

	/**
	 * get configurer for externalTransition
	 *
	 * @return {@link ExternalTransitionConfigurer}
	 */
	ExternalTransitionConfigurer<S, E, C> withExternal();
	/**
	 * get configurer for entranceTransition
	 * @param source source state
	 * @return {@link EntranceTransitionConfigurer}
	 */
	EntranceTransitionConfigurer<S, E, C> withEntrance(S source);

	/**
	 * get configurer for exitTransition
	 * @param source source state
	 * @return {@link ExitTransitionConfigurer}
	 */
    ExitTransitionConfigurer<S, E, C> withExit(S source);

    /**
	 * get configurer for internalTransition
	 *
	 * @return {@link InternalTransitionConfigurer}
	 */
	InternalTransitionConfigurer<S, E, C> withInternal();

	/**
	 * get configurer for choiceTransition
	 *
	 * @return {@link ChoiceTransitionConfigurer}
	 */
	ChoiceTransitionConfigurer<S, E, C> withChoice();

}
