package com.doubao.statemachine.state.history;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.doubao.statemachine.config.model.StatePersistModel;
import lombok.extern.slf4j.Slf4j;

/**
 * memory base history recorder
 * only for develop purpose,don't use it at production
 *
 * @param <S>
 * @param <E>
 * @param <C>
 * @author doubao
 * @date 2021/05/12
 */
@Slf4j
public class InMemoryHistoryRecorder<S, E, C> implements HistoryRecorder<S, E, C> {
	private final Function<C, String> func;
	private final Map<String, StatePersistModel<S, E, C>> storageMap = new HashMap<>();

	public InMemoryHistoryRecorder(Function<C, String> content2InstanceIdFunc) {
		this.func = content2InstanceIdFunc;
	}

	private String keyGenerate(String machineId, String instanceId, S historyState) {
		return machineId + "[" + instanceId + "(" + historyState + ")]";
	}

	private String keyGenerate(StatePersistModel<S, E, C> statePersistModel) {
		String machineId = statePersistModel.getMachineId();
		String instanceId = statePersistModel.getInstanceId();
		S historyState = statePersistModel.getHistoryState();
		return keyGenerate(machineId, instanceId, historyState);
	}

	@Override
	public void save(StatePersistModel<S, E, C> statePersistModel) {
		C content = statePersistModel.getContent();
		String instanceId = func.apply(content);
		statePersistModel.setInstanceId(instanceId);
		statePersistModel.setContent(null);
		String key = keyGenerate(statePersistModel);
		storageMap.put(key, statePersistModel);
		if (log.isDebugEnabled()) {
			log.debug("Save history state,recorder={},recordKey={},recordState={}", this.getClass().getSimpleName(),
				key, statePersistModel.getState());
		}
	}

	@Override
	public StatePersistModel<S, E, C> get(String machineId, C content, S historyState) {
		String instanceId = func.apply(content);
		String key = keyGenerate(machineId, instanceId, historyState);
		StatePersistModel<S, E, C> statePersistModel = storageMap.get(
			key);
		statePersistModel.setContent(content);
		if (log.isDebugEnabled()) {
			log.debug("Restore history state,recorder={},recordKey={},recordState={}", this.getClass().getSimpleName(),
				key, statePersistModel.getState());
		}
		return statePersistModel;
	}
}