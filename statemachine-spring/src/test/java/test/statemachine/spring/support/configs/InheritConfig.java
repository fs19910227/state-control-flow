package test.statemachine.spring.support.configs;

import com.doubao.statemachine.config.builders.StateMachineStateConfigurer;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.doubao.statemachine.extend.annotation.MachineConfig;
import test.statemachine.spring.support.config.Events;
import test.statemachine.spring.support.config.OrderTypes;
import test.statemachine.spring.support.config.States;
import test.statemachine.spring.support.guards.BackToPaid;
import test.statemachine.spring.support.model.TestOrderInfo;

/**
 * support INHERIT
 * inherit stateMachine test
 * inherit from simple
 * @author doubao
 * @date 2021/4/19
 */
@MachineConfig(id="inherit#simple",parentId = "simple")
public class InheritConfig extends BaseConfig {
	@Override
	public boolean support(TestOrderInfo content) {
		return content.getTestOrderMain().getType().equals(OrderTypes.INHERIT);
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
