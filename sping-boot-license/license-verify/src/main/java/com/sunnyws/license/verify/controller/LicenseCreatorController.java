package com.sunnyws.license.verify.controller;

import com.sunnyws.license.verify.annotion.VLicense;
import com.sunnyws.license.verify.utils.CommonUtils;
import com.sunnyws.license.verify.config.LicenseVerify;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@Api(tags = "授权接口")
@RestController
public class LicenseCreatorController {

    @Autowired
    private LicenseVerify licenseVerify;


    @GetMapping("/getCpuSerial")
    @PostConstruct
    @ApiOperation(value = "获取机器码", notes = "获取机器码")
    public String generate(){
        String cpuSerial = CommonUtils.getCpuSerial();
        log.info("-----------------机器码：{}----------------",cpuSerial);
        return cpuSerial;
    }


    @GetMapping("/verify")
    @ApiOperation(value = "验证证书", notes = "验证证书")
    public Boolean verify(){
        return licenseVerify.verify();
    }

    @GetMapping("/test")
    @ApiOperation(value = "测试接口", notes = "测试接口")
    @VLicense
    public void  test(){

    }

}