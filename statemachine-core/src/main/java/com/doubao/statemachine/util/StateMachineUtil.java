package com.doubao.statemachine.util;

import java.util.UUID;

import lombok.experimental.UtilityClass;

/**
 * common util
 *
 * @author doubao
 * @date 2021/4/30
 */
@UtilityClass
public class StateMachineUtil {

	/**
	 * random machine Id
	 *
	 * @return
	 */
	public String randomMachineId() {
		return UUID.randomUUID().toString();
	}
}
