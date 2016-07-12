package com.bowlong.third.assist;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface A2Class {
	String type() default A2G.DATA; // Server, Client, Data

	String namespace() default "";
	
	String name() default "";

	String remark() default "";

	boolean constant() default false;
}
