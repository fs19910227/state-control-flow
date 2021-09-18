package com.doubao.statemachine.extend.holder;

import java.util.function.Predicate;

import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.extend.model.MachineMetaInfo;

/**
 * DefaultStateMachineHolder
 *
 * @author doubao
 * @date 2021/05/04 19:40
 */
public class DefaultStateMachineHolder implements StateMachineHolder {
	private final StateMachine<?, ?, ?> stateMachine;
	private final Predicate<Object> supportPredicate;
	private final int priority;
	private final MachineMetaInfo metaInfo;

	public DefaultStateMachineHolder(StateMachine<?, ?, ?> stateMachine,
		MachineMetaInfo metaInfo,
		Predicate<Object> supportPredicate,
		int priority) {
		this.stateMachine = stateMachine;
		this.metaInfo = metaInfo;
		this.supportPredicate = supportPredicate;
		this.priority = priority;
	}

	@Override
	public MachineMetaInfo metaInfo() {
		return metaInfo;
	}

	@Override
	public StateMachine<?, ?, ?> stateMachine() {
		return stateMachine;
	}

	@Override
	public boolean support(Object content) {
		return supportPredicate.test(content);
	}

	@Override
	public int priority() {
		return priority;
	}
}
