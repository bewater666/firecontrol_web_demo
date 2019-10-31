package com.orient.firecontrol_web_demo.config.aop;

import java.lang.annotation.*;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/31 10:02
 * @func 自定义注解  拦截service
 */

/**
 * @Target 说明了Annotation所修饰的对象范围
 * 取值(ElementType)有：
 *      CONSTRUCTOR   用于描述构造器
 *      FIELD    用于描述域
 *      LOCAL_VARIABLE          用于描述局部变量
 *      METHOD      用于描述方法
 *      PACKAGE     用于描述包
 *      PARAMETER       用于描述参数
 *      TYPE            用于描述类、接口(包括注解类型) 或enum声明
 *
 * @Retention定义了该Annotation被保留的时间长短
 *      SOURCE:在源文件中有效（即源文件保留）
 *      CLASS:在class文件中有效（即class保留）
 *      RUNTIME:在运行时有效（即运行时保留）
 *
 * @Documented @Documented用于描述其它类型的annotation应该被作为被标注的程序成员的公共API
 *
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemServiceLog {
    String description() default "";
}
