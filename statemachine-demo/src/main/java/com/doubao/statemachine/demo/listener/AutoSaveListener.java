package com.doubao.statemachine.demo.listener;

import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.States;
import com.doubao.statemachine.demo.model.TestOrderInfo;
import com.doubao.statemachine.extend.annotation.Document;
import com.doubao.statemachine.listener.StateMachineListenerAdapter;
import com.doubao.statemachine.state.State;
import org.springframework.stereotype.Component;

/**
 * listener test
 *
 * @author doubao
 * @date 2021/4/29
 */
@Component
@Document(name = "auto-save")
public class AutoSaveListener extends StateMachineListenerAdapter<States, Events, TestOrderInfo> {

	@Override
	public void stateChanged(Events event, State<States, Events, TestOrderInfo> from,
		State<States, Events, TestOrderInfo> to, TestOrderInfo content) {
		System.out.println("Auto save state:"+to.getId());
	}
}
