package com.sunnyws.current.limit.annotation;

import com.sunnyws.current.limit.Enum.LimitKeyTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 基于Guava限流注解
 * @Author jie.zhao
 * @Date 2019/8/19 9:49
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    //资源名称
    String name() default "";

    // 限制每秒访问次数，默认最大即不限制
    double perSecond() default Double.MAX_VALUE;

    /**
     * 限流Key类型
     * 自定义根据业务唯一码来限制需要在请求参数中添加 String limitKeyValue
     */
    LimitKeyTypeEnum limitKeyType() default LimitKeyTypeEnum.IPADDR;

}