package com.doubao.statemachine.demo.guards;

import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.States;
import com.doubao.statemachine.demo.model.TestOrderInfo;
import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.extend.annotation.Document;
import com.doubao.statemachine.guard.Guard;

@Document(name = "超时", desc = "超时检测")
public class TimeOut implements Guard<States, Events, TestOrderInfo> {

	@Override
	public boolean evaluate(StateContext<States, Events, TestOrderInfo> context) {
		return false;
	}
}
