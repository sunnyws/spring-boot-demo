package com.sunnyws.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * - @Description:  CommntUtil
 * - @Author qinlang
 * - @Date 2020/7/14 15:17
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {

    /**
     * 数据code
     * 对应数据库字典表的属性名
     * @return
     */
    String dicCode();


    /**
     * put 到原始数据中key  为空则使用默认策略 dicCode + "_dictText":
     * @return
     */
    String dicText() default "";


}
