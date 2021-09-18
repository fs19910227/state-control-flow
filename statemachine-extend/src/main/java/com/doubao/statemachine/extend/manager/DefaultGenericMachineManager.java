package com.doubao.statemachine.extend.manager;

import java.util.List;

import com.doubao.statemachine.extend.model.MachineMetaInfo;
import com.doubao.statemachine.core.StateMachine;

/**
 * default generic machineManager
 *
 * @author doubao
 * @date 2021/5/27
 */
public class DefaultGenericMachineManager<S, E, C> implements GenericMachineManager<S, E, C> {
	private final StateMachineManager stateMachineManager;

	public DefaultGenericMachineManager(StateMachineManager stateMachineManager) {
		this.stateMachineManager = stateMachineManager;
	}

	@Override
	public List<MachineMetaInfo> metaInfoList() {
		return stateMachineManager.metaInfoList();
	}

	@Override
	public MachineMetaInfo metaInfoById(String id) {
		return stateMachineManager.metaInfoById(id);
	}

	@Override
	public String group() {
		return stateMachineManager.group();
	}

	@Override
	public StateMachine<S, E, C> getById(String id) {
		return stateMachineManager.getById(id);
	}

	@Override
	public StateMachine<S, E, C> selectOneByContent(C content) {
		return stateMachineManager.selectOneByContent(content);
	}
}
