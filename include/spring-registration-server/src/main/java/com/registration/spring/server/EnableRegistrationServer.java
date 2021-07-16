package com.registration.spring.server;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liu peng bo
 * date: 2021/7/14 11:55
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RegistrationServerAutoConfiguration.class)
public @interface EnableRegistrationServer {
}
