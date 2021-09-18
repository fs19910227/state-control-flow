package com.doubao.statemachine.extend.model;

import lombok.Data;

/**
 * stateMachine extend info
 *
 * @author doubao
 * @date 2021/5/14
 */
@Data
public class MachineMetaInfo {
	/**
	 * machine id
	 */
	private String id;
	/**
	 * parent machine id
	 */
	private String parentId;
	/**
	 * machine name
	 */
	private String name;
	/**
	 * machine group
	 */
	private String group;
	/**
	 * machine tag
	 */
	private String tag;
	/**
	 * machine desc
	 */
	private String desc;
}
