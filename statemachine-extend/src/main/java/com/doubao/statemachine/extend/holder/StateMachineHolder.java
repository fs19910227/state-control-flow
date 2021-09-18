package com.doubao.statemachine.extend.holder;

import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.Priority;
import com.doubao.statemachine.extend.model.MachineMetaInfo;

/**
 * StateMachine Holder
 *
 * @author doubao
 * @date 2021/05/04 19:32
 */
public interface StateMachineHolder extends Priority {
	/**
	 * get machine metaInfo
	 *
	 * @return metaInfo
	 */
	MachineMetaInfo metaInfo();

	/**
	 * get state machine
	 *
	 * @return machine
	 */
	StateMachine<?, ?, ?> stateMachine();

	/**
	 * whether the stateMachine can support the content
	 *
	 * @param content content
	 * @return
	 */
	boolean support(Object content);
}
