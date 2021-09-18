package com.doubao.statemachine.extend.manager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.Priority;
import com.doubao.statemachine.extend.holder.StateMachineHolder;
import com.doubao.statemachine.extend.model.MachineMetaInfo;
import lombok.Setter;

/**
 * DefaultStateMachineManager
 *
 * @author doubao
 * @date 2021/4/30
 */
@SuppressWarnings({"unchecked"})
public class DefaultStateMachineManager implements StateMachineManager {

	private final String group;
	@Setter
	private Map<String, StateMachineHolder> machineByIdMap;

	public DefaultStateMachineManager(String group,
		List<StateMachineHolder> machineHolders) {
		machineHolders.sort(Priority.comparator());
		this.group = group;
		this.machineByIdMap = machineHolders.stream()
			.collect(Collectors.toMap(h -> h.stateMachine().getId(), m -> m));
	}

	@Override
	public List<MachineMetaInfo> metaInfoList() {
		return machineByIdMap.values().stream()
			.map(StateMachineHolder::metaInfo)
			.collect(Collectors.toList());
	}

	@Override
	public MachineMetaInfo metaInfoById(String id) {
		StateMachineHolder secStateMachineHolder = machineByIdMap.get(id);
		if (secStateMachineHolder == null) {
			return null;
		}
		return secStateMachineHolder.metaInfo();
	}

	@Override
	public String group() {
		return group;
	}

	@Override
	public <S,E,C> StateMachine<S, E, C> getById(String id) {
		StateMachineHolder secStateMachineHolder = machineByIdMap.get(id);
		if (secStateMachineHolder == null) {
			return null;
		}
		return (StateMachine<S, E, C>)secStateMachineHolder.stateMachine();
	}

	@Override
	public <S,E,C> StateMachine<S, E, C> selectOneByContent(C content) {
		for (StateMachineHolder machineHolder : machineByIdMap.values()) {
			if (machineHolder.support(content)) {
				return (StateMachine<S, E, C>)machineHolder.stateMachine();
			}
		}
		return null;
	}
}
