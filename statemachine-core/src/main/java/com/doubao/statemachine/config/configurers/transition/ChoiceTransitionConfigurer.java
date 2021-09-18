package com.doubao.statemachine.config.configurers.transition;

import java.util.List;

import com.doubao.statemachine.guard.Guard;
import com.doubao.statemachine.action.Action;

/**
 * ChoiceTransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 21:31
 */
public interface ChoiceTransitionConfigurer<S, E, C>
        extends SourceItemConfigurer<ChoiceTransitionConfigurer<S, E, C>, S, E> {
    /**
     * specify a target state for first state
     * must be set
     *
     * @param target target state
     * @param guard  guard
     * @return configurer
     */
    ChoiceTransitionConfigurer<S, E, C> first(S target, Guard<S, E, C> guard);

    /**
     * internal choice transit
     * must have an action
     *
     * @param guard  guard
     * @param action action
     * @return configurer
     */
    ChoiceTransitionConfigurer<S, E, C> first(Guard<S, E, C> guard, Action<S, E, C> action);

    /**
     * specify a target state for first state
     * must be set
     *
     * @param target target state
     * @param guard  guard
     * @param action action
     * @return configurer
     */
    ChoiceTransitionConfigurer<S, E, C> first(S target, Guard<S, E, C> guard, Action<S, E, C> action);

    /**
     * specify a target state for first state
     * must be set
     *
     * @param target  target state
     * @param guard   guard
     * @param actions actions
     * @return configurer
     */
    ChoiceTransitionConfigurer<S, E, C> first(S target, Guard<S, E, C> guard, List<Action<S, E, C>> actions);

    /**
     * specify a target state for then choice
     * option choice,support multiple
     *
     * @param target target state
     * @param guard  guard
     * @return configurer
     */
    ChoiceTransitionConfigurer<S, E, C> then(S target, Guard<S, E, C> guard);

    /**
     * specify a target state for then choice
     * option choice,support multiple
     *
     * @param target target state
     * @param guard  guard
     * @param action action
     * @return configurer
     */
    ChoiceTransitionConfigurer<S, E, C> then(S target, Guard<S, E, C> guard, Action<S, E, C> action);

    /**
     * specify a target state for then choice
     * option choice,support multiple
     *
     * @param target  target state
     * @param guard   guard
     * @param actions actions
     * @return configurer
     */
    ChoiceTransitionConfigurer<S, E, C> then(S target, Guard<S, E, C> guard, List<Action<S, E, C>> actions);

    /**
     * specify a target state for last choice
     * must be set
     *
     * @param target target state
     * @return configurer
     */
    ChoiceTransitionConfigurer<S, E, C> last(S target);

    /**
     * specify a target state for last choice
     * must be set
     *
     * @param target target state
     * @param action action
     * @return configurer
     */
    ChoiceTransitionConfigurer<S, E, C> last(S target, Action<S, E, C> action);

    /**
     * specify a target state for last choice
     * must be set
     *
     * @param target  target state
     * @param actions actions
     * @return configurer
     */
    ChoiceTransitionConfigurer<S, E, C> last(S target, List<Action<S, E, C>> actions);
}
