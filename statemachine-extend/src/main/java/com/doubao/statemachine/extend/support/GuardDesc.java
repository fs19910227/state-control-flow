package com.doubao.statemachine.extend.support;

import com.doubao.statemachine.extend.annotation.Document;
import com.doubao.statemachine.guard.Guard;
import lombok.Data;

/**
 * guard info
 *
 * @author doubao
 * @date 2021/5/28
 */
@Data
public class GuardDesc {
	private String name;
	private String desc;
	private Class<? extends Guard> guardClass;
	public GuardDesc(Guard<?, ?, ?> guard) {
		this.guardClass = guard.getClass();
		Document annotation = this.guardClass.getAnnotation(Document.class);
		if (annotation != null) {
			this.name = annotation.name();
			this.desc = annotation.desc();
		} else {
			this.name = guardClass.getSimpleName();
		}
	}

	public GuardDesc(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}
}
