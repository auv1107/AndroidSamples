package com.sctdroid.app.samples.annotations;

import android.support.annotation.DrawableRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lixindong2 on 5/21/18.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentMenuItem {
    String title() default "";
    @DrawableRes int iconRes() default 0;
    boolean checked() default false;
    int id() default 0;
    int groupId() default 0;
}
