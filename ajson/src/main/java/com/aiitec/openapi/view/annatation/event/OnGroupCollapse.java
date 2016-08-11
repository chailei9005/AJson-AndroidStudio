package com.aiitec.openapi.view.annatation.event;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ExpandableListView收缩监听
 * @author Anthony
 * createTime 2015-12-30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnGroupCollapse {
    int value() default -1;
    int[] ids() default {};
}
