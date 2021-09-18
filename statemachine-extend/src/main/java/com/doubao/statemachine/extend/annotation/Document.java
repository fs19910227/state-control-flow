package com.doubao.statemachine.extend.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 文档标注注解
 * 用于状态机MetaInfo 填充
 * 可用于Guard,Action
 *
 * @author doubao
 * @date 2021/5/28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
public @interface Document {
	/**
	 * name
	 *
	 * @return name
	 */
	String name();

	/**
	 * desc
	 *
	 * @return desc
	 */
	String desc() default "";
}
