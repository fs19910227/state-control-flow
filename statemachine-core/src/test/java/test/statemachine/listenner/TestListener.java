package test.statemachine.listenner;

import com.doubao.statemachine.listener.StateMachineListener;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.state.State;
import com.doubao.statemachine.transition.Transition;
import com.doubao.statemachine.transition.TransitionKind;
import test.statemachine.config.Events;
import test.statemachine.config.States;
import test.statemachine.model.TestOrderInfo;

/**
 * listener test
 *
 * @author doubao
 * @date 2021/4/29
 */
public class TestListener implements StateMachineListener<States, Events, TestOrderInfo> {

	@Override
	public void stateChanged(Events event, State<States, Events, TestOrderInfo> from,
		State<States, Events, TestOrderInfo> to, TestOrderInfo content) {
		String format = String.format("Listen state changed :from=%s,to=%s,content=%s", from,
			to, content);
		System.out.println(format);
	}

	@Override
	public void transitionStarted(Transition<States, Events, TestOrderInfo> transition,
		Message<States, Events, TestOrderInfo> message) {
		State<States, Events, TestOrderInfo> source = transition.getSource();
		State<States, Events, TestOrderInfo> target = transition.getTarget();
		TransitionKind kind = transition.getKind();
		Events event = transition.getEvent();
		String format = String.format("Listen transition start:kind=%s,from=%s,to=%s,event=%s", kind.toString(),
			source,
			target, event);
		System.out.println(format);
	}

	@Override
	public void transitionSucceed(Transition<States, Events, TestOrderInfo> transition,
		Message<States, Events, TestOrderInfo> message) {
		State<States, Events, TestOrderInfo> source = transition.getSource();
		State<States, Events, TestOrderInfo> target = transition.getTarget();
		TransitionKind kind = transition.getKind();
		Events event = transition.getEvent();
		String format = String.format("Listen transition end:kind=%s,from=%s,to=%s,event=%s", kind.toString(), source,
			target, event);
		System.out.println(format);

	}

	@Override
	public void transitionNotFound(Message<States, Events, TestOrderInfo> message) {

	}

	@Override
	public void eventNotAccepted(Message<States, Events, TestOrderInfo> message) {
		States source = message.getSource();
		TestOrderInfo content = message.getContent();
		Events event = message.getEvent();
		String format = String.format("Listen event not accepted :source=%s,event=%s,content=%s", source, event,
			content);
		System.out.println(format);

	}
}
