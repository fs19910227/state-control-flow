package test.statemachine.spring.support.configs;

import com.doubao.statemachine.config.builders.StateMachineStateConfigurer;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.doubao.statemachine.extend.annotation.MachineConfig;
import test.statemachine.spring.support.config.Events;
import test.statemachine.spring.support.config.OrderTypes;
import test.statemachine.spring.support.config.States;
import test.statemachine.spring.support.model.TestOrderInfo;

/**
 * support SIMPLE
 * simple stateMachine test
 *
 * @author doubao
 * @date 2021/4/19
 */
@MachineConfig(id = "simple")
public class SimpleConfig extends BaseConfig {
    @Override
    public boolean support(TestOrderInfo content) {
        return content.getTestOrderMain().getType().equals(OrderTypes.SIMPLE);
    }

    @Override
    public void configureStates(StateMachineStateConfigurer<States, Events, TestOrderInfo> stateConfigurer) {
        stateConfigurer.withStates()
                .startState(States.START)
                .finalState(States.OPENED);
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
                .external(Events.PAY, States.PAID)
                //check
                .internal(Events.CHECK, context -> System.out.println("check only,don't change state"));

        transitionConfigurer
                .withSource(States.PAID)
                //open
                .external(Events.OPEN, States.OPENED);

    }
}
