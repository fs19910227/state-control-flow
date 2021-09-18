package com.doubao.statemachine.spring.autoconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.doubao.statemachine.spring.autoconfig.StateMachineProperties.PREFIX;

/**
 * state machine configs
 *
 * @author doubao
 * @date 2021/5/27
 */
@ConfigurationProperties(prefix = PREFIX)
@Getter
@Setter
public class StateMachineProperties {
	public static final String PREFIX = "statemachine";
	/**
	 * 是否开启generic manager
	 */
	private boolean genericManager = false;
	/**
	 * 是否开启webserver
	 */
	private boolean webserver = false;

}
