package com.aiitec.openapi.view.annatation.event;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnTouch {
//	String methodName() default "onTouch";
    int value() default -1;
    int[] ids() default {};
}
