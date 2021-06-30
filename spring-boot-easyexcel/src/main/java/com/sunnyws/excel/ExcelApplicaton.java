package com.sunnyws.excel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * - @Description:  ExcelApplicaton
 * - @Author qinlang
 * - @Date 2021/6/16
 * - @Version 1.0
 **/
@MapperScan("com.sunnyws.excel.mapper.**")
@SpringBootApplication
public class ExcelApplicaton {
    public static void main(String[] args) {
        SpringApplication.run(ExcelApplicaton.class, args);
    }
}
