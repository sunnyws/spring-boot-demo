package com.sunnyws.dict.annotation;
/*
* @Description //TODO
* @Author qinlang
* @Date 10:26 2020/9/8
**/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DictMethod {
}
