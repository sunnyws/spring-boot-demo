package com.sunnyws.job.annotation;

import com.sunnyws.job.config.XxlJobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/*
* @Description //TODO
* @Author qinlang
* @Date 9:23 2020/9/14
**/

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ XxlJobAutoConfiguration.class })
public @interface EnableXxlJob {

}
