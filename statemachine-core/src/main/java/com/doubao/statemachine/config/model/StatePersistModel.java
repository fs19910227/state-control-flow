package com.doubao.statemachine.config.model;

import lombok.Data;

/**
 * state persist model
 *
 * @author doubao
 * @date 2021/5/12
 */
@Data
public class StatePersistModel<S, E, C> {
	private String machineId;
	private String instanceId;
	private transient C content;
	private S historyState;
	private S state;
}
