package com.aiitec.openapi.view.annatation.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnSeekBarChange {
    
    String stopTrackingTouchMethodName() default "onStopTrackingTouch";
    String startTrackingTouchMethodName() default "onStartTrackingTouch";
//    String progressChangedMethodName() default "onProgressChanged";
    int value() default -1;
    int[] ids() default {};
}
