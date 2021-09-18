package com.doubao.statemachine.config.configurers;

import com.doubao.statemachine.config.builders.Builder;

/**
 * @author doubao
 * @date 2021/4/28
 */
public abstract class ConfigurerAdapter<M, B extends Builder<M>> {
	protected B builder;

	/**
	 * method for Configurer to build model data by builder
	 * will be called when start to build state machine model
	 *
	 * @param builder
	 */
	public abstract void configure(B builder);

	public void setBuilder(B builder) {
		this.builder = builder;
	}
}
