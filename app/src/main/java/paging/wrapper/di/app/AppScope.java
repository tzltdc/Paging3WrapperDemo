package paging.wrapper.di.app;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import javax.inject.Scope;

/**
 * Identifies a type that the injector only instantiates once. Not inherited.
 *
 * @see javax.inject.Scope @Scope
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface AppScope {}
