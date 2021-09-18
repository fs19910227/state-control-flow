package com.doubao.statemachine.extend.support.uml;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * uml generator config properties
 *
 * @author doubao
 * @date 2021/06/01
 */
@Data
@Accessors(chain = true)
public class UmlGeneratorConfigProperties {
	/**
	 * eventViewMode
	 * default all
	 */
	private EventViewMode eventViewMode = EventViewMode.ALL;
	/**
	 * whether show internal
	 * default false
	 */
	private boolean showInternalTransition = false;
	/**
	 * whether show action
	 * default false
	 */
	private boolean showAction = false;

}