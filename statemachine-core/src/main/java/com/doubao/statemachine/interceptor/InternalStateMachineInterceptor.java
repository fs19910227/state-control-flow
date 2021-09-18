package com.doubao.statemachine.interceptor;

import com.doubao.statemachine.transition.Transition;
import com.doubao.statemachine.content.ContentStateChanger;
import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.state.State;
import lombok.Setter;

/**
 * Listener for internal use
 *
 * @author doubao
 * @date 2021/4/29
 */
public class InternalStateMachineInterceptor<S, E, C> extends StateMachineInterceptorAdapter<S, E, C> {
	@Setter
	private ContentStateChanger<S, C> contentStateChanger;

	@Override
	public void preAction(State<S, E, C> state, Message<S, E, C> message, Transition<S, E, C> transition,
		StateMachine<S, E, C> stateMachine) {
		if (contentStateChanger != null && state != null) {
			C content = message.getContent();
			contentStateChanger.onChange(state.getId(), content);
		}
	}
}
