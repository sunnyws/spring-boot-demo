package com.sunnyws.log.annotation;


import java.lang.annotation.*;

import com.sunnyws.log.enums.BusinessType;

/*
* @Description //TODO
* @Author qinlang
* @Date 10:45 2020/9/9
**/

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    String title() default "";

    /**
     * 功能
     */
    BusinessType businessType() default BusinessType.OTHER;


    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存返回的参数
     */
    boolean isSaveReponseData() default true;
}
