package com.doubao.statemachine.demo.interceptor;

import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.States;
import com.doubao.statemachine.demo.model.TestOrderInfo;
import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.interceptor.StateMachineInterceptor;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.state.State;
import com.doubao.statemachine.transition.Transition;

/**
 * @author doubao
 * @date 2021/4/29
 */
public class AutoSaveInterceptor implements StateMachineInterceptor<States, Events, TestOrderInfo> {
	@Override
	public void preAction(State<States, Events, TestOrderInfo> state,
		Message<States, Events, TestOrderInfo> message, Transition<States, Events, TestOrderInfo> transition,
		StateMachine<States, Events, TestOrderInfo> stateMachine) {
		System.out.println("Interceptor auto save state :" + state.getId());
	}
}
