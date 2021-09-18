package com.doubao.statemachine.config.builders;

import com.doubao.statemachine.config.configurers.state.StateConfigurer;

/**
 * StateMachineTransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 21:27
 */
public interface StateMachineStateConfigurer<S, E, C> {


	StateConfigurer<S, E, C> withStates();



}
