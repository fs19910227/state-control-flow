package com.doubao.statemachine.spring.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.doubao.statemachine.spring.config.ConfigRegistrar;
import org.springframework.context.annotation.Import;

/**
 * @author doubao
 * @date 2021/4/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({ConfigRegistrar.class})
public @interface StateMachineScan {
	String[] value() default {};
}
