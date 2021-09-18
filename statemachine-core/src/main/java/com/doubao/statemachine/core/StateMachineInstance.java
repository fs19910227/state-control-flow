package com.doubao.statemachine.core;

import java.util.Set;

import com.doubao.statemachine.state.State;

/**
 * StateMachine instance
 *
 * @author doubao
 * @date 2021/4/28
 */
public interface StateMachineInstance<S, E, C> {
	/**
	 * get content
	 *
	 * @return C
	 */
	C content();

	/**
	 * 返回当前状态
	 *
	 * @return currentState
	 */
	State<S, E, C> currentState();

	/**
	 * get id
	 *
	 * @return id
	 */
	StateMachine<S, E, C> getStateMachine();


	/**
	 * process event and execute actions
	 *
	 * @param event
	 * @return true /false
	 */
	boolean fire(E event);

	/**
	 * infer next possible events
	 *
	 * @return
	 */
	Set<E> inferNextPossibleEvents();
}
