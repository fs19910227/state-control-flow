package com.doubao.statemachine.config.configurers.transition;

import java.util.List;

import com.doubao.statemachine.guard.Guard;
import com.doubao.statemachine.action.Action;

/**
 * abstract normal TransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 22:17
 */
public abstract class AbstractNormalTransitionConfigurer<S, E, C> extends AbstractTransitionConfigurer<S, E, C> {
	protected Guard<S, E, C> guard;
	protected List<Action<S, E, C>> actions;


}
