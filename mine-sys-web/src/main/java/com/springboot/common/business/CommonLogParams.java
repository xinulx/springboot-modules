package com.springboot.common.business;

import java.lang.annotation.*;

@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CommonLogParams {

    public Class value();// class

    public String type();// business type

    public String desc() default "";// business desc

    public String method();// method
}
