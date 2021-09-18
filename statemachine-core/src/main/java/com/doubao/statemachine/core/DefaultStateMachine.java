package com.doubao.statemachine.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.doubao.statemachine.Result;
import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.config.model.StateMachineModel;
import com.doubao.statemachine.interceptor.DefaultInterceptorManager;
import com.doubao.statemachine.listener.StateMachineEventPublisher;
import com.doubao.statemachine.listener.StateMachineListener;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.state.StateType;
import com.doubao.statemachine.state.history.HistoryState;
import com.doubao.statemachine.transition.Transition;
import com.doubao.statemachine.interceptor.StateMachineInterceptor;
import com.doubao.statemachine.state.State;
import lombok.Getter;
import lombok.Setter;

/**
 * DefaultStateMachine
 *
 * @author doubao
 * @date 2021/4/28
 */
public class DefaultStateMachine<S, E, C> implements StateMachine<S, E, C> {
	private final String id;
	private StateMachine<S, E, C> child;
	private StateMachine<S, E, C> parent;
	@Getter
	private final StateMachineModel<S, E, C> stateMachineModel;
	private final StateMachineEventPublisher<S, E, C> eventPublisher;
	private final DefaultInterceptorManager<S, E, C> interceptorManager;
	private final TransitionManager<S, E, C> transitionManager;
	@Getter
	@Setter
	private Map<S, State<S, E, C>> stateMap = new HashMap<>();
	private List<HistoryState<S, E, C>> historyStates;

	public DefaultStateMachine(String id, StateMachineModel<S, E, C> stateMachineModel) {
		this.id = id;
		this.stateMachineModel = stateMachineModel;
		this.eventPublisher = new StateMachineEventPublisher<>();
		this.interceptorManager = new DefaultInterceptorManager<>();
		this.transitionManager = new TransitionManager<>();
	}

	public void init() {
		transitionManager.init(interceptorManager,eventPublisher);
		this.historyStates = stateMap.values().stream()
			.filter(s -> s.getType() == StateType.HISTORY)
			.map(s -> (HistoryState<S, E, C>)s)
			.collect(Collectors.toList());
	}

	@Override
	public Set<E> inferNextPossibleEvents(S source) {
		return transitionManager.inferNextPossibleEvents(source);
	}

	@Override
	public String getId() {
		return id;
	}

	private Result<S, E, C> fireWithContext(StateContext<S, E, C> context) {
		Message<S, E, C> message = context.getMessage();
		List<Transition<S, E, C>> transitions = transitionManager.selectMatchTransitions(message);
		for (Transition<S, E, C> transition : transitions) {
			context.setTransition(transition);
			interceptorManager.preProcessTransition(context);
			boolean transit = transition.transit(context);
			State<S, E, C> target = null;
			if (transit) {
				State<S, E, C> source = transition.getSource();
				target = context.getTarget();
				if (target != null && !source.getId().equals(target.getId())) {
					eventPublisher.notifyStateChanged(message.getEvent(), source, target, message.getContent());
				}
			}
			return Result.of(true, transit, target, message.getContent());
		}
		eventPublisher.notifyTransitionNotFound(message);
		return Result.of(false, false, null, message.getContent());
	}

	@Override
	public Result<S, E, C> fire(Message<S, E, C> message) {
		StateContext<S, E, C> context = new StateContext<S, E, C>()
			.setSource(stateMap.get(message.getSource()))
			.setMachineId(id)
			.setStateMachine(this)
			.setMessage(message)
			.setHistoryStates(historyStates)
			.setStateMap(stateMap);
		return fireWithContext(context);
	}

	@Override
	public StateMachineModel<S, E, C> getModel() {
		return stateMachineModel;
	}

	@Override
	public StateMachine<S, E, C> getChild() {
		return child;
	}

	@Override
	public StateMachineInstanceBuilder<S, E, C> newInstanceBuilder() {
		return new StateMachineInstanceBuilder<>(this);
	}

	private void setChild(
		StateMachine<S, E, C> child) {
		this.child = child;
		StateMachineModel<S, E, C> childModel = child.getModel();
		this.stateMachineModel.setChild(childModel);
	}

	public void setParent(
		StateMachine<S, E, C> parent) {
		this.parent = parent;
		if (parent != null) {
			((DefaultStateMachine<S, E, C>)parent).setChild(this);
		}
	}

	public void setTransitionMap(
		Map<S, List<Transition<S, E, C>>> transitionMap) {
		this.transitionManager.setTransitionMap(transitionMap);

	}

	public void setInterceptors(
		List<StateMachineInterceptor<S, E, C>> interceptors) {
		interceptorManager.init(interceptors);
	}

	public void setStateMachineListeners(List<StateMachineListener<S, E, C>> stateMachineListeners) {
		eventPublisher.init(stateMachineListeners);
	}
}
