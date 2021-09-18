package com.doubao.statemachine.listener;

import java.util.List;
import java.util.function.Consumer;

import com.doubao.statemachine.Priority;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.transition.Transition;
import com.doubao.statemachine.state.State;

/**
 * StateMachineEventPublisher
 * use to publish message to listeners
 *
 * @author doubao
 * @date 2021/4/29
 */
public class StateMachineEventPublisher<S, E, C> {
	private List<StateMachineListener<S, E, C>> stateMachineListeners;

	public void init(List<StateMachineListener<S, E, C>> listeners) {
		if (listeners != null) {
			listeners.sort(Priority.comparator());
		}
		this.stateMachineListeners = listeners;
	}

	private void executePublish(Consumer<StateMachineListener<S, E, C>> listenerConsumer) {
		if (stateMachineListeners == null) {
			return;
		}
		for (StateMachineListener<S, E, C> stateMachineListener : stateMachineListeners) {
			try {
				listenerConsumer.accept(stateMachineListener);
			} catch (Exception exception) {
				//cannot pause message publish when error happened
				//todo think how to do
				exception.printStackTrace();
			}
		}
	}

	public void notifyStateChanged(E event, State<S, E, C> from, State<S, E, C> to, C content) {
		executePublish(listener -> listener.stateChanged(event, from, to, content));
	}

	public void notifyTransitionStart(Transition<S, E, C> transition,
                                      Message<S, E, C> message) {
		executePublish(listener -> listener.transitionStarted(transition, message));
	}

	public void notifyTransitionSuccess(Transition<S, E, C> transition,
		Message<S, E, C> message) {
		executePublish(listener -> listener.transitionSucceed(transition, message));
	}

	public void notifyTransitionEventNotAccepted(Message<S, E, C> message) {
		executePublish(listener -> listener.eventNotAccepted(message));
	}

	public void notifyTransitionNotFound(Message<S, E, C> message) {
		executePublish(listener -> listener.transitionNotFound(message));
	}
}
