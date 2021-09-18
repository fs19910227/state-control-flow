package com.doubao.statemachine.demo.configs;

import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.OrderTypes;
import com.doubao.statemachine.demo.model.States;
import com.doubao.statemachine.demo.model.TestOrderInfo;
import com.doubao.statemachine.config.builders.StateMachineStateConfigurer;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.doubao.statemachine.demo.actions.TestAction;
import com.doubao.statemachine.extend.annotation.MachineConfig;

/**
 * support SIMPLE
 * simple stateMachine test
 *
 * @author doubao
 * @date 2021/4/19
 */
@MachineConfig(id="simple",name = "简单状态机配置",tag = "simple")
public class SimpleConfig extends BaseConfig {
	@Override
	public boolean support(TestOrderInfo content) {
		return content.getTestOrderMain().getType().equals(OrderTypes.SIMPLE);
	}

	@Override
	public void configureStates(StateMachineStateConfigurer<States, Events, TestOrderInfo> stateConfigurer) {
		stateConfigurer.withStates()
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
			.external(Events.CANCEL, States.CANCELED, new TestAction())
			//pay
			.external(Events.PAY, States.PAID)
			//check
			.internal(Events.CHECK, context -> System.out.println("check only,don't change state"));

		transitionConfigurer
			.withSource(States.PAID)
			//open
			.external(Events.OPEN, States.OPENED);

	}
}
