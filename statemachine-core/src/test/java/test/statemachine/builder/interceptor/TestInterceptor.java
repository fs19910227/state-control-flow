package test.statemachine.builder.interceptor;

import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.interceptor.StateMachineInterceptorAdapter;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.state.State;
import com.doubao.statemachine.transition.Transition;
import test.statemachine.config.Events;
import test.statemachine.config.States;
import test.statemachine.model.TestOrderInfo;

/**
 * @author doubao
 * @date 2021/4/29
 */
public class TestInterceptor extends StateMachineInterceptorAdapter<States, Events, TestOrderInfo> {
	@Override
	public void preAction(State<States, Events, TestOrderInfo> state,
		Message<States, Events, TestOrderInfo> message, Transition<States, Events, TestOrderInfo> transition,
		StateMachine<States, Events, TestOrderInfo> stateMachine) {
		System.out.println("Interceptor auto save state :" + state.getId());

	}

	@Override
	public void postTransition(State<States, Events, TestOrderInfo> state,
		Message<States, Events, TestOrderInfo> message, Transition<States, Events, TestOrderInfo> transition,
		StateMachine<States, Events, TestOrderInfo> stateMachine) {

	}

	@Override
	public void preTransition(State<States, Events, TestOrderInfo> state,
		Message<States, Events, TestOrderInfo> message, Transition<States, Events, TestOrderInfo> transition,
		StateMachine<States, Events, TestOrderInfo> stateMachine) {

	}
}
