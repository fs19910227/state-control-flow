package test.statemachine.spring.support.configs;

import com.doubao.statemachine.config.builders.StateMachineStateConfigurer;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.doubao.statemachine.extend.annotation.MachineConfig;
import test.statemachine.spring.support.config.Events;
import test.statemachine.spring.support.config.OrderTypes;
import test.statemachine.spring.support.config.States;
import test.statemachine.spring.support.guards.AllOpen;
import test.statemachine.spring.support.guards.AllPaid;
import test.statemachine.spring.support.guards.AllRefund;
import test.statemachine.spring.support.model.TestOrderInfo;

/**
 * 复杂订单配置
 *
 * @author doubao
 * @date 2021/4/19
 */
@MachineConfig(id="composite")
public class CompositeConfig extends BaseConfig {
	@Override
	public boolean support(TestOrderInfo content) {
		return content.getTestOrderMain().getType().equals(OrderTypes.COMPOSITE);
	}

	@Override
	public void configureStates(StateMachineStateConfigurer<States, Events, TestOrderInfo> stateConfigurer) {
		stateConfigurer.withStates()
			.startState(States.START)
			.choiceState(States.choice_on_open)
			.choiceState(States.choice_on_refund_deny)
			.choiceState(States.choice_on_refund_pass);
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
			//pay
			.external(Events.PAY, States.PAID);

		transitionConfigurer
			.withSource(States.PAID)
			//open
			.external(Events.OPEN, States.choice_on_open)
			//commit refund
			.external(Events.REFUND_COMMIT, States.REFUNDING);

		AllOpen allOpenGuard = new AllOpen();
		AllPaid allPaidGuard = new AllPaid();
		transitionConfigurer
			.withSource(States.OPENING)
			//open
			.external(Events.OPEN, States.OPENED, allOpenGuard);

		transitionConfigurer
			.withSource(States.OPENED)
			//commit refund
			.external(Events.REFUND_COMMIT, States.REFUNDING);

		AllRefund allRefundGuard = new AllRefund();
		transitionConfigurer.withSource(States.REFUNDING)
			//refund pass
			.external(Events.REFUND_PASS, States.choice_on_refund_pass)
			//refund deny
			.external(Events.REFUND_DENNY, States.choice_on_refund_deny);

		//open choice
		transitionConfigurer.withChoice()
			.source(States.choice_on_open)
			.first(States.OPENED, allOpenGuard)
			.last(States.OPENING);

		//refund pass choice
		transitionConfigurer.withChoice()
			.source(States.choice_on_refund_pass)
			.first(States.REFUND_SUCCEED, allRefundGuard)
			.last(States.REFUNDING);

		//refund deny choice
		transitionConfigurer.withChoice()
			.source(States.choice_on_refund_deny)
			.first(States.PAID, allPaidGuard)
			.then(States.OPENED, allOpenGuard)
			.last(States.REFUNDING);

	}



}
