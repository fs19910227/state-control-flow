package com.doubao.statemachine.core;

import java.util.Set;

import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.Result;
import com.doubao.statemachine.state.State;
import lombok.Setter;

/**
 * Default stateMachine instance
 *
 * @author doubao
 * @date 2021/5/8
 */
public class DefaultStateMachineInstance<S, E, C> implements StateMachineInstance<S, E, C> {
	/**
	 * current state
	 */
	@Setter
	private State<S, E, C> currentState;
	/**
	 * content
	 */
	private final C content;
	/**
	 * stateMachine
	 */
	private final DefaultStateMachine<S, E, C> stateMachine;

	public DefaultStateMachineInstance(S currentState, C content,
		StateMachine<S, E, C> stateMachine) {
		this.stateMachine = (DefaultStateMachine<S, E, C>)stateMachine;
		this.content = content;
		this.currentState = this.stateMachine.getStateMap().get(currentState);
	}

	@Override
	public StateMachine<S, E, C> getStateMachine() {
		return stateMachine;
	}

	@Override
	public C content() {
		return content;
	}

	@Override
	public State<S, E, C> currentState() {
		return currentState;
	}


	@Override
	public boolean fire(E event) {

		Message<S, E, C> message = Message.of(currentState.getId(), event, content);
		Result<S, E, C> fire = stateMachine.fire(message);
		State<S, E, C> targetState = fire.getTargetState();
		if (targetState != null) {
			currentState = targetState;
		}
		return fire.isSucceed();
	}

	@Override
	public Set<E> inferNextPossibleEvents() {
		return stateMachine.inferNextPossibleEvents(currentState.getId());
	}
}
