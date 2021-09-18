package com.doubao.statemachine.demo.configs;

import java.util.EnumSet;

import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.OrderTypes;
import com.doubao.statemachine.demo.model.States;
import com.doubao.statemachine.demo.model.TestOrderInfo;
import com.doubao.statemachine.config.builders.StateMachineStateConfigurer;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.doubao.statemachine.extend.annotation.MachineConfig;
import com.doubao.statemachine.state.history.InMemoryHistoryRecorder;

/**
 * support HISTORY
 * history stateMachine test
 * with history state
 *
 * @author doubao
 * @date 2021/4/19
 */
@MachineConfig(id="history",name = "历史节点配置")
public class HistoryConfig extends BaseConfig {

	@Override
	public boolean support(TestOrderInfo content) {
		return content.getTestOrderMain().getType().equals(OrderTypes.HISTORY);
	}

	@Override
	public void configureStates(StateMachineStateConfigurer<States, Events, TestOrderInfo> stateConfigurer) {
		stateConfigurer.withStates()
			.historyRecorder(new InMemoryHistoryRecorder<>(content -> content.getTestOrderMain().getOrderNo()))
			.historyState(States.HISTORY, EnumSet.of(States.PAID, States.OPENED))
			.startState(States.START);
	}



	@Override
	public void configureTransitions(
		StateMachineTransitionConfigurer<States, Events, TestOrderInfo> transitionConfigurer) {
		transitionConfigurer
			.withSource(States.START)
			//create
			.external(Events.CREATE, States.UN_PAID);
		transitionConfigurer
			.withSource(States.UN_PAID)
			//cancel
			.external(Events.CANCEL, States.CANCELED)
			//pay
			.external(Events.PAY, States.PAID);

		transitionConfigurer
			.withSource(States.PAID)
			//open
			.external(Events.OPEN, States.OPENED)
			//refund
			.external(Events.REFUND_COMMIT, States.REFUND_AUDITING);

		transitionConfigurer
			.withSource(States.OPENED)
			//refund
			.external(Events.REFUND_COMMIT, States.REFUND_AUDITING);

		transitionConfigurer
			.withSource(States.REFUND_AUDITING)
			//refund deny
			.external(Events.REFUND_DENNY, States.HISTORY)
			//refund pass
			.external(Events.REFUND_PASS, States.REFUND_SUCCEED);
	}
}
