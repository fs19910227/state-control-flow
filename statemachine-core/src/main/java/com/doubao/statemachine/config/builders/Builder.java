package com.doubao.statemachine.config.builders;

import java.util.ArrayList;
import java.util.List;

import com.doubao.statemachine.config.configurers.ConfigurerAdapter;

/**
 * @author doubao
 * @date 2021/4/28
 */
public abstract class Builder<M> {
	/**
	 * configures
	 */
	protected final List<ConfigurerAdapter> configurers = new ArrayList<>();

	/**
	 * apply configurer
	 *
	 * @param configurer
	 * @param <T>
	 * @return
	 */
	protected <B extends Builder<M>, T extends ConfigurerAdapter<M, B>> T apply(T configurer) {
		configurers.add(configurer);
		configurer.setBuilder((B)this);
		return configurer;
	}

	protected <B extends Builder<M>> void doConfig() {
		configurers.forEach(configurer -> configurer.configure(this));
	}

	/**
	 * build config info
	 * @return
	 */
	public abstract M build();
}
