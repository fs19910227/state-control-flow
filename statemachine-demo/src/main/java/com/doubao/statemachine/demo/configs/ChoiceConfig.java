package com.doubao.statemachine.demo.configs;

import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.OrderTypes;
import com.doubao.statemachine.demo.model.States;
import com.doubao.statemachine.demo.model.TestOrderInfo;
import com.doubao.statemachine.config.builders.StateMachineStateConfigurer;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.doubao.statemachine.demo.actions.TestAction;
import com.doubao.statemachine.demo.guards.BackToPaid;
import com.doubao.statemachine.demo.guards.TimeOut;
import com.doubao.statemachine.extend.annotation.MachineConfig;

/**
 * support CHOICE
 * history stateMachine test
 * with history state
 *
 * @author doubao
 * @date 2021/4/19
 */
@MachineConfig(id="choice",name = "choice分支配置",tag = "choice")
public class ChoiceConfig extends BaseConfig {
	@Override
	public boolean support(TestOrderInfo content) {
		return content.getTestOrderMain().getType().equals(OrderTypes.CHOICE);
	}

	@Override
	public void configureStates(StateMachineStateConfigurer<States, Events, TestOrderInfo> stateConfigurer) {
		stateConfigurer.withStates()
			.choiceState(States.choice_on_refund_deny)
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
			.external(Events.CANCEL, States.CANCELED,new TimeOut())
			//pay
			.external(Events.PAY, States.PAID);

		transitionConfigurer
			.withSource(States.PAID)
			//internal check
			.internal(Events.CHECK, context -> System.out.println("check"))
			//open
			.external(Events.OPEN, States.OPENED)
			//refund
			.external(Events.REFUND_COMMIT, States.REFUND_AUDITING, new TestAction());

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
