package com.doubao.statemachine.demo.actions;

import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.States;
import com.doubao.statemachine.demo.model.TestOrderInfo;
import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.extend.annotation.Document;

/**
 * @author doubao
 * @date 2021/05/28 23:44
 */
@Document(name = "测试Action name", desc = "测试Action desc")
public class TestAction implements Action<States, Events, TestOrderInfo> {
	@Override
	public void evaluate(StateContext<States, Events, TestOrderInfo> context) {

	}
}
