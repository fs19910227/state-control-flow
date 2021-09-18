package com.doubao.statemachine.core;

import java.util.Set;

import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.Result;
import com.doubao.statemachine.config.model.StateMachineModel;

/**
 * StateMachine Definition
 *
 * @author doubao
 * @date 2021/4/28
 */
public interface StateMachine<S, E, C> {
	/**
	 * get child stateMachine
	 *
	 * @return
	 */
	StateMachine<S, E, C> getChild();

	/**
	 * get stateMachineModel
	 *
	 * @return
	 */
	StateMachineModel<S, E, C> getModel();

	/**
	 * new StateMachineInstance
	 *
	 * @return instance
	 */
	StateMachineInstanceBuilder<S, E, C> newInstanceBuilder();

	/**
	 * get id
	 *
	 * @return id
	 */
	String getId();

	/**
	 * process event and execute actions
	 * fire with basic message
	 *
	 * @param message
	 * @return true /false
	 */
	Result<S, E, C> fire(Message<S, E, C> message);

	/**
	 * infer next possible events from source state
	 *
	 * @param source source state
	 * @return
	 */
	Set<E> inferNextPossibleEvents(S source);

}
