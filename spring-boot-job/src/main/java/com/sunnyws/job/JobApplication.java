package com.sunnyws.job;

import com.sunnyws.job.annotation.EnableXxlJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * - @description:  JobApplication
 * - @author qinlang
 * - @date 2020/9/12 14:10
 */

@EnableXxlJob
@SpringBootApplication
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}
