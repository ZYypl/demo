package com.example.demo.annotation;

/**
 * com.example.demo.annotation
 *
 * @author ypl
 * @create 2020-11-02 09:25
 */

import java.lang.annotation.*;

/**
 10  * 自定义操作日志注解
 12  */

@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface OperLog {
    // 操作模块
     String operModul() default "";

    // 操作类型
     String operType() default "";

    // 操作说明
     String operDesc() default "";

 }
