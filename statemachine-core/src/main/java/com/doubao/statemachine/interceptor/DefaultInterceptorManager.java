package com.doubao.statemachine.interceptor;

import java.util.List;
import java.util.function.Consumer;

import com.doubao.statemachine.Priority;
import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.state.State;
import com.doubao.statemachine.transition.Transition;

/**
 * manager of interceptors
 *
 * @author doubao
 * @date 2021/5/7
 */
public class DefaultInterceptorManager<S, E, C> implements InterceptorManager<S, E, C> {
    private List<StateMachineInterceptor<S, E, C>> interceptors;

    public void init(List<StateMachineInterceptor<S, E, C>> stateMachineInterceptors) {
        if (stateMachineInterceptors != null) {
            stateMachineInterceptors.sort(Priority.comparator());
        }
        this.interceptors = stateMachineInterceptors;
    }

    private void apply(Consumer<StateMachineInterceptor<S, E, C>> consumer) {
        for (StateMachineInterceptor<S, E, C> interceptor : interceptors) {
            consumer.accept(interceptor);
        }
    }

    @Override
    public void preProcessGuard(StateContext<S, E, C> context) {
        StateMachine<S, E, C> stateMachine = context.getStateMachine();
        State<S, E, C> target = context.getTarget();
        Message<S, E, C> message = context.getMessage();
        Transition<S, E, C> transition = context.getTransition();
        apply(interceptor -> interceptor.preGuard(target, message, transition, stateMachine));
    }

    @Override
    public void preProcessAction(StateContext<S, E, C> context) {
        StateMachine<S, E, C> stateMachine = context.getStateMachine();
        State<S, E, C> target = context.getTarget();
        Message<S, E, C> message = context.getMessage();
        Transition<S, E, C> transition = context.getTransition();
        apply(interceptor -> interceptor.preAction(target, message, transition, stateMachine));
    }

    @Override
    public void postProcessAction(StateContext<S, E, C> context) {
        StateMachine<S, E, C> stateMachine = context.getStateMachine();
        State<S, E, C> target = context.getTarget();
        Message<S, E, C> message = context.getMessage();
        Transition<S, E, C> transition = context.getTransition();
        apply(interceptor -> interceptor.postAction(target, message, transition, stateMachine));
    }

    @Override
    public void preProcessTransition(StateContext<S, E, C> context) {
        StateMachine<S, E, C> stateMachine = context.getStateMachine();
        State<S, E, C> target = context.getTarget();
        Message<S, E, C> message = context.getMessage();
        Transition<S, E, C> transition = context.getTransition();
        apply(interceptor -> interceptor.preTransition(target, message, transition, stateMachine));

    }

    @Override
    public void postProcessTransition(StateContext<S, E, C> context) {
        StateMachine<S, E, C> stateMachine = context.getStateMachine();
        State<S, E, C> target = context.getTarget();
        Message<S, E, C> message = context.getMessage();
        Transition<S, E, C> transition = context.getTransition();
        apply(interceptor -> interceptor.postTransition(target, message, transition, stateMachine));

    }
}
