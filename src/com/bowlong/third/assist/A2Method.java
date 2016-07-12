package com.bowlong.third.assist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface A2Method {
	String type() default A2G.SERVER;

	String[] params();

	String remark() default "";
}
