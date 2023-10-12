package se.pbt.socialalert.annotation;

import io.micronaut.http.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to associate an HTTP status with an exception.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpStatusAnnotation {

    /**
     * Value representing the HTTP status.
     */
    HttpStatus value();
}
