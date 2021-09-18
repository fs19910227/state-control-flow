package com.doubao.statemachine;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.state.history.HistoryState;
import com.doubao.statemachine.transition.Transition;
import com.doubao.statemachine.state.State;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * state context
 *
 * @author doubao
 * @date 2021/04/27 22:02
 */
@Data
@Accessors(chain = true)
public class StateContext<S, E, C> {
	/**
	 * stateMachine id
	 */
	private String machineId;
	/**
	 * context id
	 * use random uuid
	 * unique
	 */
	private String contextId;
	/**
	 * message
	 */
	private Message<S, E, C> message;
	/**
	 * source state
	 */
	private State<S, E, C> source;
	/**
	 * target state
	 */
	private State<S, E, C> target;
	/**
	 * stateMachine
	 */
	private StateMachine<S, E, C> stateMachine;
	/**
	 * transition
	 */
	private Transition<S, E, C> transition;
	/**
	 * historyStates
	 */
	private List<HistoryState<S, E, C>> historyStates;
	/**
	 * stateMap
	 */
	private Map<S, State<S, E, C>> stateMap;

	public StateContext() {
		this.contextId = UUID.randomUUID().toString();
	}
}
