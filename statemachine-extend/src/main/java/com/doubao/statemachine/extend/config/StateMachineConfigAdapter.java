package com.doubao.statemachine.extend.config;

/**
 * config adapter
 *
 * @author doubao
 * @date 2021/4/30
 */
public abstract class StateMachineConfigAdapter<S, E, C> implements StateMachineConfig<S, E, C> {

	@Override
	public boolean support(C content) {
		//default support all
		return true;
	}

	@Override
	public int priority() {
		return LOWEST_PRIORITY;
	}
}
