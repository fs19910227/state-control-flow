package com.doubao.statemachine.extend.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.doubao.statemachine.extend.config.StateMachineConfig;

/**
 * @author doubao
 * @date 2021/5/7
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
public @interface MachineConfig {
	/**
	 * machine id
	 *
	 * @return
	 */
	String id() default "";
	/**
	 * machine name
	 *
	 * @return name
	 */
	String name() default "";

	/**
	 * state machine description
	 * for doc support
	 *
	 * @return
	 */
	String desc() default "";

	/**
	 * state machine group
	 * each group will be managed by a StateMachineManager
	 * @return
	 */
	String group() default StateMachineConfig.DEFAULT_GROUP;

	/**
	 * state machine tag
	 *
	 * @return
	 */
	String tag() default "default";
	/**
	 * parent id
	 * associate with parent state machine
	 *
	 * @return
	 */
	String parentId() default "";

	/**
	 * indicate whether inherit configs from parent state machine
	 * not work if don't have a parent id
	 *
 	 * @return
	 */
	boolean inherit() default true;
}
