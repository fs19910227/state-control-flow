package com.doubao.statemachine.extend.support;

import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.extend.annotation.Document;
import lombok.Data;

/**
 * action info
 *
 * @author doubao
 * @date 2021/5/28
 */
@Data
public class ActionDesc {
	private String name;
	private String source;
	private String event;
	private String desc;
	private Class<? extends Action> actionClass;



	public ActionDesc(Action<?, ?, ?> action) {
		this.actionClass = action.getClass();
		Document annotation = this.actionClass.getAnnotation(Document.class);
		if (annotation != null) {
			this.name = annotation.name();
			this.desc = annotation.desc();
		} else {
			this.name = actionClass.getSimpleName();
		}
	}
}
