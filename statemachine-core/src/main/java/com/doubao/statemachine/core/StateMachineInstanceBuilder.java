package com.doubao.statemachine.core;

/**
 * instance builder
 *
 * @author doubao
 * @date 2021/5/8
 */
public class StateMachineInstanceBuilder<S, E, C> {
	private final StateMachine<S, E, C> stateMachine;
	private S initState;
	private C contentContext;

	public StateMachineInstanceBuilder(StateMachine<S, E, C> stateMachine) {
		this.stateMachine = stateMachine;
	}

	public StateMachineInstanceBuilder<S, E, C> initState(S initState) {
		this.initState = initState;
		return this;
	}

	public StateMachineInstanceBuilder<S, E, C> content(C contentContext) {
		this.contentContext = contentContext;
		return this;
	}

	public StateMachineInstance<S, E, C> build() {
		return new DefaultStateMachineInstance<>(initState,
			contentContext, stateMachine);
	}
}
