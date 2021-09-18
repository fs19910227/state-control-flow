package com.doubao.statemachine.demo.configs;

import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.OrderTypes;
import com.doubao.statemachine.demo.model.States;
import com.doubao.statemachine.demo.model.TestOrderInfo;
import com.doubao.statemachine.config.builders.StateMachineStateConfigurer;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.doubao.statemachine.demo.guards.BackToPaid;
import com.doubao.statemachine.extend.annotation.MachineConfig;

/**
 * support INHERIT
 * inherit stateMachine test
 * inherit from simple
 * @author doubao
 * @date 2021/4/19
 */
@MachineConfig(id = "inherit", parentId = "simple",name = "配置继承")
public class InheritConfig extends BaseConfig {
	@Override
	public boolean support(TestOrderInfo content) {
		return content.getTestOrderMain().getType().equals(OrderTypes.SIMPLE);
	}

	@Override
	public void configureStates(StateMachineStateConfigurer<States, Events, TestOrderInfo> stateConfigurer) {
		stateConfigurer.withStates()
			.choiceState(States.choice_on_refund_deny);
	}


	@Override
	public void configureTransitions(
		StateMachineTransitionConfigurer<States, Events, TestOrderInfo> transitionConfigurer) {
		transitionConfigurer
			.withSource(States.PAID)
			//refund
			.external(Events.REFUND_COMMIT, States.REFUND_AUDITING);

		transitionConfigurer
			.withSource(States.OPENED)
			//refund
			.external(Events.REFUND_COMMIT, States.REFUND_AUDITING);

		transitionConfigurer
			.withSource(States.REFUND_AUDITING)
			//refund deny
			.external(Events.REFUND_DENNY, States.choice_on_refund_deny)
			//refund pass
			.external(Events.REFUND_PASS, States.REFUND_SUCCEED);
		//choice on refund deny
		transitionConfigurer
			.withChoice()
			.source(States.choice_on_refund_deny)
			.first(States.PAID, new BackToPaid())
			.last(States.OPENED);

	}
}
