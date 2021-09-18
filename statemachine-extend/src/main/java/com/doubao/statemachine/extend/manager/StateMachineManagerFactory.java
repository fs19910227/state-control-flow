package com.doubao.statemachine.extend.manager;

/**
 * StateMachineManagerFactory
 *
 * @author doubao
 * @date 2021/4/30
 */
public interface StateMachineManagerFactory {
	/**
	 * get stateMachineManager By group
	 *
	 * @param group group
	 * @return
	 */
	 StateMachineManager getStateMachineManager(String group);
}
