package com.doubao.statemachine.state.history;

import com.doubao.statemachine.config.model.StatePersistModel;

/**
 * history information recorder
 *
 * @author doubao
 * @date 2021/5/12
 */
public interface HistoryRecorder<S, E, C> {
	/**
	 * save state
	 *
	 * @param statePersistModel
	 */
	void save(StatePersistModel<S, E, C> statePersistModel);

	/**
	 * get state
	 *
	 * @param machineId
	 * @param content
	 * @param historyState
	 */
	StatePersistModel<S,E,C> get(String machineId, C content, S historyState);
}
