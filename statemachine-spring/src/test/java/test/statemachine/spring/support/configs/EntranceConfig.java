package test.statemachine.spring.support.configs;

import com.doubao.statemachine.config.builders.StateMachineStateConfigurer;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.doubao.statemachine.extend.annotation.MachineConfig;
import com.doubao.statemachine.state.history.InMemoryHistoryRecorder;
import test.statemachine.spring.support.config.Events;
import test.statemachine.spring.support.config.OrderTypes;
import test.statemachine.spring.support.config.States;
import test.statemachine.spring.support.guards.TimeOut;
import test.statemachine.spring.support.model.TestOrderInfo;

import java.util.EnumSet;

import static test.statemachine.spring.support.config.States.*;

/**
 * support entrance
 *
 * @author doubao
 * @date 2021/4/19
 */
@MachineConfig(id = "entrance")
public class EntranceConfig extends BaseConfig {

    @Override
    public boolean support(TestOrderInfo content) {
        return content.getTestOrderMain().getType().equals(OrderTypes.ENTRANCE);
    }

    @Override
    public void configureStates(StateMachineStateConfigurer<States, Events, TestOrderInfo> stateConfigurer) {
        stateConfigurer.withStates()
                .historyState(States.HISTORY, EnumSet.of(PAID,OPENED))
                .historyRecorder(new InMemoryHistoryRecorder<>(c->c.getTestOrderMain().getOrderNo()))
                .entrance(e1)
                .stateGroup("Refund",States.REFUNDING, REFUND_AUDITING, e1)
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
                .external(Events.CANCEL, States.CANCELED, new TimeOut())
                //pay
                .external(Events.PAY, States.PAID);

        transitionConfigurer
                .withSource(States.PAID)
                //internal check
                .internal(Events.CHECK, context -> System.out.println("check"))
                //open
                .external(Events.OPEN, States.OPENED)
                //refund
                .external(Events.REFUND_COMMIT, States.e1);

        transitionConfigurer
                .withSource(States.OPENED)
                //refund
                .external(Events.REFUND_COMMIT, States.e1);
        transitionConfigurer
                .withEntrance(e1)
                .target(REFUND_AUDITING);
        transitionConfigurer
                .withSource(REFUND_AUDITING)
                //refund deny
                .external(Events.REFUND_DENNY, HISTORY)
                //refund pass
                .external(Events.REFUND_PASS, States.REFUNDING);
        transitionConfigurer
                .withSource(States.REFUNDING)
                //refund success
                .external(Events.REFUND_SUCCESS, States.REFUND_SUCCEED);
    }
}
