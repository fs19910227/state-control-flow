package com.doubao.statemachine.web.controller.vo;

import com.doubao.statemachine.extend.support.uml.EventViewMode;
import lombok.Data;

/**
 * @author doubao
 * @date 2021/6/2
 */
@Data
public class UmlView {
	private boolean showInternal = false;
	private boolean showAction = false;
	private EventViewMode eventViewMode;
	private EventViewMode[] eventViewModes = EventViewMode.values();
	/**
	 * uml code
	 */
	private String umlCode;
	/**
	 * img
	 */
	private String umlImg;
}
