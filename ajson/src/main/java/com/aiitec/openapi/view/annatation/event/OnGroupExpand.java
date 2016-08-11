package com.aiitec.openapi.view.annatation.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ExpandableListView展开监听
 * @author Anthony
 * createTime 2015-12-31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnGroupExpand {
//	String methodName() default "onGroupExpand";
    int value() default -1;
    int[] ids() default {};
}
