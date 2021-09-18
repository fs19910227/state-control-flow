package com.doubao.statemachine.state.history;

import java.util.Set;

import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.config.model.StatePersistModel;
import com.doubao.statemachine.state.ObjectState;
import com.doubao.statemachine.state.State;
import com.doubao.statemachine.state.StateType;

/**
 * history state definition
 *
 * @author doubao
 * @date 2021/5/12
 */
public class HistoryState<S, E, C> extends ObjectState<S, E, C> {
	private final Set<S> focusStates;
	private final HistoryRecorder<S, E, C> historyRecorder;

	public HistoryState(S id, Set<S> focusStates, HistoryRecorder<S, E, C> recorder) {
		super(id, StateType.HISTORY);
		this.focusStates = focusStates;
		this.historyRecorder = recorder;
	}

	public void record(StateContext<S, E, C> context) {
		State<S, E, C> target = context.getTarget();
		if (target == null) {
			return;
		}
		if (!focusStates.contains(target.getId())) {
			return;
		}

		StatePersistModel<S, E, C> statePersistModel = new StatePersistModel<>();
		statePersistModel.setMachineId(context.getMachineId());
		statePersistModel.setHistoryState(this.getId());
		statePersistModel.setContent(context.getMessage().getContent());
		statePersistModel.setState(target.getId());
		historyRecorder.save(statePersistModel);

	}

	public StatePersistModel<S, E, C> get(StateContext<S, E, C> context) {
		String machineId = context.getMachineId();
		C content = context.getMessage().getContent();
		return historyRecorder.get(machineId, content, this.getId());
	}
}
