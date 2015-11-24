package pl.java.scalatech.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface CatchException {
    boolean sendEmail() default false;

    boolean sendLog() default false;

    boolean sendDS() default true;

}
