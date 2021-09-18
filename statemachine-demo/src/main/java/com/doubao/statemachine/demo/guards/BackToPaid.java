package com.doubao.statemachine.demo.guards;

import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.States;
import com.doubao.statemachine.demo.model.TestOrderInfo;
import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.extend.annotation.Document;
import com.doubao.statemachine.guard.Guard;

@Document(name = "回退到已支付", desc = "拉取退款信息，确定是否回退到已支付")
public class BackToPaid implements Guard<States, Events, TestOrderInfo> {
	@Override
	public boolean evaluate(StateContext<States, Events, TestOrderInfo> context) {
		return true;
	}
}