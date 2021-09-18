package com.doubao.statemachine.demo.configs;

import com.doubao.statemachine.demo.listener.AutoSaveListener;
import com.doubao.statemachine.demo.listener.ConsoleLogListener;
import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.States;
import com.doubao.statemachine.demo.model.TestOrderInfo;
import com.doubao.statemachine.config.builders.StateMachineConfigurationConfigurer;
import com.doubao.statemachine.extend.config.StateMachineConfigAdapter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * base config
 * @author doubao
 * @date 2021/05/15 22:52
 */
public abstract class BaseConfig  extends StateMachineConfigAdapter<States, Events, TestOrderInfo> {
	@Autowired
	private AutoSaveListener autoSaveListener;
	@Autowired
	private ConsoleLogListener consoleLogListener;
	@Override
	public void configureConfiguration(StateMachineConfigurationConfigurer<States, Events, TestOrderInfo> configurer) {
		//支持校验自检
		configurer.withVerifier()
			.enabled(false);
		configurer
			.withConfiguration()
			.listener(autoSaveListener)
			.listener(consoleLogListener)
			//通过contentStateChanger自动变更TestOrderInfo里的状态，基于拦截器
			.contentStateChanger((s, c) -> c.getTestOrderMain().setStates(s));
	}
}
