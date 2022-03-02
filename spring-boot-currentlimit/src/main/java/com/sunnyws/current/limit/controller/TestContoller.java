package com.sunnyws.current.limit.controller;

import com.sunnyws.current.limit.Enum.LimitKeyTypeEnum;
import com.sunnyws.current.limit.annotation.Limit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * - @Description:  TestContoller
 * - @Author qinlang
 * - @Date 2021/8/26
 * - @Version 1.0
 **/

@RestController
public class TestContoller {

    @Limit(name = "测试限流每秒1个请求",perSecond = 1,limitKeyType = LimitKeyTypeEnum.IPADDR)
    @GetMapping("/test")
    public void test(){
//        try {
//            Thread.sleep(500);
//        }catch (Exception e){
//
//        }
    }

    @GetMapping("/test1")
    public String  test1(){
        return "test";
    }
}
