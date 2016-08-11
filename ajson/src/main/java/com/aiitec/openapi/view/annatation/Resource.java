package com.aiitec.openapi.view.annatation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {

    int value() default -1;
    int stringId() default -1;
    int stringArrayId() default -1;
    int intArrayId() default -1;
    int drawableId() default -1;
}
