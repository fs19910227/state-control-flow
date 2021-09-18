package com.doubao.statemachine.transition;

import java.util.List;

import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.core.TransitionManager;
import com.doubao.statemachine.interceptor.InterceptorManager;
import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.guard.Guard;
import com.doubao.statemachine.listener.StateMachineEventPublisher;
import com.doubao.statemachine.state.State;
import lombok.Setter;

/**
 * abstract transition
 *
 * @author doubao
 * @date 2021/4/28
 */
public abstract class AbstractTransition<S, E, C> implements Transition<S, E, C> {
    protected State<S, E, C> source;
    protected E event;
    protected Guard<S, E, C> guard;
    protected List<Action<S, E, C>> actions;
    @Setter
    protected InterceptorManager<S, E, C> interceptorManager;
    @Setter
    protected StateMachineEventPublisher<S, E, C> eventPublisher;
    @Setter
    protected TransitionManager<S, E, C> transitionManager;

    public AbstractTransition(State<S, E, C> source, E event,
                              Guard<S, E, C> guard, List<Action<S, E, C>> actions) {
        this.source = source;
        this.event = event;
        this.guard = guard;
        this.actions = actions;
    }

    /**
     * process guard
     *
     * @param context context
     * @return true ->success
     * false -->fail
     */
    protected boolean processGuard(StateContext<S, E, C> context) {
        if (guard != null) {
            interceptorManager.preProcessGuard(context);
            boolean evaluate = guard.evaluate(context);
            if (!evaluate && event != null) {
                eventPublisher.notifyTransitionEventNotAccepted(context.getMessage());
            }
            return evaluate;
        }
        return true;
    }

    protected void processActions(StateContext<S, E, C> context) {
        if (actions != null && !actions.isEmpty()) {
            interceptorManager.preProcessAction(context);
            for (Action<S, E, C> action : actions) {
                action.beforeAction(context);
                try {
                    action.evaluate(context);
                } catch (Throwable throwable) {
                    action.onException(context, throwable);
                }
                action.afterAction(context);
            }
            interceptorManager.postProcessAction(context);
        }
    }

    @Override
    public State<S, E, C> getSource() {
        return source;
    }

    @Override
    public Guard<S, E, C> getGuard() {
        return guard;
    }

    @Override
    public List<Action<S, E, C>> getActions() {
        return actions;
    }

    @Override
    public E getEvent() {
        return event;
    }
}
