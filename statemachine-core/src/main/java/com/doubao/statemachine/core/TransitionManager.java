package com.doubao.statemachine.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.doubao.statemachine.interceptor.InterceptorManager;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.transition.Transition;
import com.doubao.statemachine.Priority;
import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.listener.StateMachineEventPublisher;
import com.doubao.statemachine.transition.AbstractTransition;
import lombok.Setter;

/**
 * @author doubao
 * @date 2021/5/10
 */
public class TransitionManager<S, E, C> {
	@Setter
	private Map<S, List<Transition<S, E, C>>> transitionMap;

	public void init(InterceptorManager<S, E, C> interceptorManager, StateMachineEventPublisher<S,E,C> eventPublisher) {
		//sort actions
		transitionMap.forEach((s, list) -> {
			for (Transition<S, E, C> transition : list) {
				AbstractTransition<S, E, C> abstractTransition = (AbstractTransition<S, E, C>)transition;
				List<Action<S, E, C>> actions = abstractTransition.getActions();
				if (actions != null) {
					actions.sort(Priority.comparator());
				}
			}
		});

		//fill interceptorManager,eventPublisher
		transitionMap.forEach((s, list) -> {
			for (Transition<S, E, C> transition : list) {
				AbstractTransition<S, E, C> abstractTransition = (AbstractTransition<S, E, C>)transition;
				abstractTransition.setEventPublisher(eventPublisher);
				abstractTransition.setInterceptorManager(interceptorManager);
				abstractTransition.setTransitionManager(this);
			}
		});
	}

	public Set<E> inferNextPossibleEvents(S source) {
		return transitionMap.getOrDefault(source, Collections.emptyList()).stream()
			.filter(t -> t.getEvent() != null)
			.map(Transition::getEvent)
			.collect(Collectors.toSet());
	}

	public List<Transition<S, E, C>> selectMatchTransitions(Message<S, E, C> message) {
		S current = message.getSource();
		E event = message.getEvent();
		List<Transition<S, E, C>> transitions = transitionMap.getOrDefault(current, Collections.emptyList());
		transitions = transitions.stream()
			.filter(t -> t.getEvent() == event)
			.collect(Collectors.toList());
		return transitions;
	}
}
