package test.statemachine.spring.support.configs;

import com.doubao.statemachine.config.builders.StateMachineStateConfigurer;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.doubao.statemachine.extend.annotation.MachineConfig;
import org.springframework.beans.factory.annotation.Autowired;
import test.statemachine.spring.support.config.Events;
import test.statemachine.spring.support.config.OrderTypes;
import test.statemachine.spring.support.config.States;
import test.statemachine.spring.support.guards.BackToPaid;
import test.statemachine.spring.support.guards.TimeOut;
import test.statemachine.spring.support.listener.AutoSaveListener;
import test.statemachine.spring.support.listener.ConsoleLogListener;
import test.statemachine.spring.support.model.TestOrderInfo;

/**
 * support CHOICE
 *
 * @author doubao
 * @date 2021/4/19
 */
@MachineConfig(id = "choice")
public class ChoiceConfig extends BaseConfig {
    @Autowired
    private AutoSaveListener autoSaveListener;
    @Autowired
    private ConsoleLogListener consoleLogListener;

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
                .external(Events.REFUND_PASS, States.REFUNDING);
        //choice on refund deny
        transitionConfigurer
                .withChoice()
                .source(States.choice_on_refund_deny)
                .first(States.PAID, new BackToPaid())
                .last(null);
        transitionConfigurer
                .withSource(States.REFUNDING)
                //refund success
                .external(Events.REFUND_SUCCESS, States.REFUND_SUCCEED);
    }
}
