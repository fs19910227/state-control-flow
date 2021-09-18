package com.doubao.statemachine.demo.configs;

import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.OrderTypes;
import com.doubao.statemachine.demo.model.States;
import com.doubao.statemachine.demo.model.TestOrderInfo;
import com.doubao.statemachine.config.builders.StateMachineStateConfigurer;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.doubao.statemachine.demo.guards.TimeOut;
import com.doubao.statemachine.extend.annotation.MachineConfig;
import com.doubao.statemachine.state.history.InMemoryHistoryRecorder;

import java.util.EnumSet;


/**
 * support entrance
 *
 * @author doubao
 * @date 2021/4/19
 */
@MachineConfig(id = "in-out", name = "入口,出口点配置", desc = "group ,entrance,exit")
public class InOutConfig extends BaseConfig {

    @Override
    public boolean support(TestOrderInfo content) {
        return content.getTestOrderMain().getType().equals(OrderTypes.ENTRANCE);
    }

    @Override
    public void configureStates(StateMachineStateConfigurer<States, Events, TestOrderInfo> stateConfigurer) {
        stateConfigurer.withStates()
                .historyState(States.HISTORY, EnumSet.of(States.PAID, States.OPENED))
                .historyRecorder(new InMemoryHistoryRecorder<>(c -> c.getTestOrderMain().getOrderNo()))
                .entrance(States.in)
                .exit(States.out)
                .stateGroup("pay", States.PAID)
                .stateGroup("Refund", States.REFUNDING, States.REFUND_AUDITING, States.REFUND_SUCCEED, States.in, States.out)
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
                .external(Events.REFUND_COMMIT, States.in);

        transitionConfigurer
                .withSource(States.OPENED)
                .external(Events.COMPLETE, States.COMPLETED)
                //refund
                .external(Events.REFUND_COMMIT, States.in);
        transitionConfigurer
                .withSource(States.REFUND_AUDITING)
                //refund deny
                .external(Events.REFUND_DENNY, States.HISTORY)
                //refund pass
                .external(Events.REFUND_PASS, States.REFUNDING);
        transitionConfigurer
                .withSource(States.REFUNDING)
                //refund success
                .external(Events.REFUND_SUCCESS, States.REFUND_SUCCEED);
        transitionConfigurer
                .withSource(States.REFUND_SUCCEED)
                //complete
                .external(Events.COMPLETE, States.out);

        transitionConfigurer
                .withEntrance(States.in)
                .target(States.REFUND_AUDITING);
        transitionConfigurer
                .withExit(States.out)
                .target(States.COMPLETED);
    }
}
