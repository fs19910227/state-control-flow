package com.doubao.statemachine.extend.config;

import java.util.Collections;
import java.util.List;

import com.doubao.statemachine.interceptor.StateMachineInterceptor;
import com.doubao.statemachine.listener.StateMachineListener;
import lombok.Data;

/**
 * global config
 *
 * @author doubao
 * @date 2021/6/2
 */
@Data
public class StateMachineGlobalConfig {
	/**
	 * config group
	 */
	private String group = StateMachineConfig.DEFAULT_GROUP;
	/**
	 * global interceptors
	 */
	private List<StateMachineInterceptor<?, ?, ?>> globalInterceptors = Collections.emptyList();
	/**
	 * global listeners
	 */
	private List<StateMachineListener<?, ?, ?>> globalListeners = Collections.emptyList();
}
