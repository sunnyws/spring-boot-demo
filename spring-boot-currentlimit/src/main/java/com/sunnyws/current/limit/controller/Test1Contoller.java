package com.sunnyws.current.limit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * - @Description:  TestContoller
 * - @Author qinlang
 * - @Date 2021/8/26
 * - @Version 1.0
 **/

@RestController
public class Test1Contoller {


    @GetMapping("/test2")
    public String  test2(){
        return "test2";
    }
}
