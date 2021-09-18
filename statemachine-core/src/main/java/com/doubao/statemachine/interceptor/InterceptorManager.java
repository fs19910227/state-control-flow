package com.doubao.statemachine.interceptor;

import com.doubao.statemachine.transition.Transition;
import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.state.State;

/**
 * InterceptorManager
 *
 * @author doubao
 * @date 2021/5/7
 */
public interface InterceptorManager<S, E, C> {
    /**
     * pre process before guard performed
     *
     * @param context context
     * @see StateMachineInterceptor#preGuard(State, Message, Transition, StateMachine)
     */
    void preProcessGuard(StateContext<S, E, C> context);

    /**
     * pre process before action performed
     *
     * @param context context
     * @see StateMachineInterceptor#preAction(State, Message, Transition, StateMachine)
     */
    void preProcessAction(StateContext<S, E, C> context);

    /**
     * post process after action performed
     *
     * @param context context
     * @see StateMachineInterceptor#postAction(State, Message, Transition, StateMachine)
     */
    void postProcessAction(StateContext<S, E, C> context);

    /**
     * pre process before transit
     *
     * @param context context
     * @see StateMachineInterceptor#preTransition(State, Message, Transition, StateMachine)
     */
    void preProcessTransition(StateContext<S, E, C> context);

    /**
     * post process after transit with state change
     *
     * @param context context
     * @see StateMachineInterceptor#postTransition(State, Message, Transition, StateMachine)
     */
    void postProcessTransition(StateContext<S, E, C> context);
}
