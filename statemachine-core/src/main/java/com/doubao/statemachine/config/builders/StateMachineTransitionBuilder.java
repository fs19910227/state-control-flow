package com.doubao.statemachine.config.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.doubao.statemachine.config.configurers.transition.*;
import com.doubao.statemachine.config.model.TransitionData;
import com.doubao.statemachine.config.configurers.transition.simple.DefaultSourceBaseTransitionConfigurer;
import com.doubao.statemachine.config.configurers.transition.simple.SourceBaseTransitionConfigurer;
import com.doubao.statemachine.config.model.ChoiceData;
import com.doubao.statemachine.config.model.TransitionsData;

/**
 * StateMachine Transition Builder
 *
 * @author doubao
 * @date 2021/04/27 21:20
 */
public class StateMachineTransitionBuilder<S, E, C>
	extends Builder<TransitionsData<S, E, C>>
	implements StateMachineTransitionConfigurer<S, E, C> {

	/**
	 * transitions data list
	 */
	private final List<TransitionData<S, E, C>> transitionData = new ArrayList<>();
	/**
	 * choice data map
	 */
	private final Map<S, List<ChoiceData<S, E, C>>> choiceMap = new HashMap<>();

	@Override
	public SourceBaseTransitionConfigurer<S, E, C> withSource(S source) {
		return apply(new DefaultSourceBaseTransitionConfigurer<>(source));
	}

	@Override
	public ExternalTransitionConfigurer<S, E, C> withExternal() {
		return apply(new DefaultExternalTransitionConfigurer<>());
	}

	@Override
	public EntranceTransitionConfigurer<S, E, C> withEntrance(S source) {
		return apply(new DefaultEntranceTransitionConfigurer<>(source));
	}

	@Override
	public ExitTransitionConfigurer<S, E, C> withExit(S source) {
		return apply(new DefaultExitTransitionConfigurer<>(source));
	}
	@Override
	public InternalTransitionConfigurer<S, E, C> withInternal() {
		return apply(new DefaultInternalTransitionConfigurer<>());
	}

	@Override
	public ChoiceTransitionConfigurer<S, E, C> withChoice() {
		return apply(new DefaultChoiceTransitionConfigurer<>());
	}

	public void addTransition(TransitionData<S, E, C> data) {
		transitionData.add(data);
	}

	public void addChoice(S source, List<ChoiceData<S, E, C>> choiceData) {
		choiceMap.put(source,choiceData);
	}

	@Override
	public TransitionsData<S, E, C> build() {
		super.doConfig();
		TransitionsData<S, E, C> transitionsData = new TransitionsData<>();
		transitionsData.setTransitionDataList(transitionData);
		transitionsData.setChoiceMap(choiceMap);
		return transitionsData;
	}
}
