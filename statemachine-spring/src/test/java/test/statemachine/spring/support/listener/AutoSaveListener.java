package test.statemachine.spring.support.listener;

import com.doubao.statemachine.listener.StateMachineListener;
import com.doubao.statemachine.state.State;
import org.springframework.stereotype.Component;
import test.statemachine.spring.support.config.Events;
import test.statemachine.spring.support.config.States;
import test.statemachine.spring.support.model.TestOrderInfo;

/**
 * listener test
 *
 * @author doubao
 * @date 2021/4/29
 */
@Component
public class AutoSaveListener implements StateMachineListener<States, Events, TestOrderInfo> {

    @Override
    public void stateChanged(Events event,
                             State<States, Events, TestOrderInfo> from,
                             State<States, Events, TestOrderInfo> to, TestOrderInfo content) {
        System.out.println("Auto save state:" + to.getId());
    }
}
