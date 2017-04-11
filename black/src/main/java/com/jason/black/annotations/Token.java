package com.jason.black.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fuyongde on 2017/4/8.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {

    /**
     * Get a token.
     *
     * @return the boolean
     */
    boolean get() default false;

    /**
     * Auth the token.
     *
     * @return the boolean
     */
    boolean auth() default false;
}
