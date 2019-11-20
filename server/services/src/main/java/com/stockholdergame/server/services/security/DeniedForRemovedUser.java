package com.stockholdergame.server.services.security;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Alexander Savin
 *         Date: 17.2.12 19.15
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface DeniedForRemovedUser {
}
