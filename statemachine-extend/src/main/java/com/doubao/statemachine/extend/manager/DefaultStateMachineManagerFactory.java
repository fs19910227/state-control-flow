package com.doubao.statemachine.extend.manager;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.doubao.statemachine.extend.loader.StateMachineConfigLoader;
import com.doubao.statemachine.extend.config.StateMachineConfig;
import com.doubao.statemachine.extend.config.StateMachineGlobalConfig;
import com.doubao.statemachine.extend.holder.StateMachineHolder;

/**
 * StateMachineManagerFactory
 *
 * @author doubao
 * @date 2021/4/30
 */
@SuppressWarnings({"rawtypes"})
public class DefaultStateMachineManagerFactory implements
	StateMachineManagerFactory {
	private Map<String, StateMachineManager> stateMachineManagerMap;

	public void init(Collection<StateMachineConfig> configs, Map<String, StateMachineGlobalConfig> globalConfigMap) {
		if (globalConfigMap == null) {
			globalConfigMap = Collections.emptyMap();
		}
		Map<String, List<StateMachineHolder>> load = StateMachineConfigLoader.load(configs, globalConfigMap);
		stateMachineManagerMap = new HashMap<>(load.size());
		for (Entry<String, List<StateMachineHolder>> entry : load.entrySet()) {
			String group = entry.getKey();
			List<StateMachineHolder> machines = entry.getValue();
			DefaultStateMachineManager defaultStateMachineManager = new DefaultStateMachineManager(group, machines);
			stateMachineManagerMap.put(group, defaultStateMachineManager);
		}
	}

	@Override
	public StateMachineManager getStateMachineManager(String group) {
		return stateMachineManagerMap.get(group);
	}
}
