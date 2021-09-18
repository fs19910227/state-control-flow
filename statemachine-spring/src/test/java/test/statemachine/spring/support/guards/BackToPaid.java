package test.statemachine.spring.support.guards;

import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.guard.Guard;
import test.statemachine.spring.support.config.Events;
import test.statemachine.spring.support.config.States;
import test.statemachine.spring.support.model.TestOrderInfo;

public class BackToPaid implements Guard<States, Events, TestOrderInfo> {
	@Override
	public boolean evaluate(StateContext<States, Events, TestOrderInfo> context) {
		return true;
	}
}