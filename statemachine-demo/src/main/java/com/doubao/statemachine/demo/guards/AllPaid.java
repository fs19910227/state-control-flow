package com.doubao.statemachine.demo.guards;

import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.States;
import com.doubao.statemachine.demo.model.TestOrderInfo;
import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.guard.Guard;

public class AllPaid implements Guard<States, Events, TestOrderInfo> {

	@Override
	public boolean evaluate(StateContext<States, Events, TestOrderInfo> context) {
		return true;
	}
}