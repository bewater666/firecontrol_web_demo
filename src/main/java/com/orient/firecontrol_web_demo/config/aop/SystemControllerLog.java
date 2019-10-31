package com.orient.firecontrol_web_demo.config.aop;

import java.lang.annotation.*;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/31 10:03
 * @func    自定义注解 拦截Controller
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {
    String description() default "";
}
