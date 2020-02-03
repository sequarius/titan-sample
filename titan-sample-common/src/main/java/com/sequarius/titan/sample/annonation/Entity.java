package com.sequarius.titan.sample.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * project titan
 *
 * @author Sequarius *
 * @since 14/01/2020
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Entity {
    String name();
    String displayName();
}
