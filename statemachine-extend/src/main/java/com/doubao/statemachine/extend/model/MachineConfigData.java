package com.doubao.statemachine.extend.model;

import com.doubao.statemachine.extend.config.StateMachineConfig;
import lombok.Data;

/**
 * @author doubao
 * @date 2021/5/14
 */
@Data
@SuppressWarnings({"rawtypes"})
public class MachineConfigData {
	private StateMachineConfig config;
	private MachineMetaInfo metaInfo;

	public MachineConfigData(StateMachineConfig config, MachineMetaInfo metaInfo) {
		this.config = config;
		this.metaInfo = metaInfo;
	}
}
