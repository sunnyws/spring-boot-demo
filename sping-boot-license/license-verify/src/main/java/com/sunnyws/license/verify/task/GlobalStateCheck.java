package com.sunnyws.license.verify.task;

import com.sunnyws.license.verify.config.GlobalState;
import com.sunnyws.license.verify.config.LicenseVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * - @Description:  GlobalStateCheck
 * - @Author qinlang
 * - @Date 2021/8/19
 * - @Version 1.0
 **/

@Component
@Slf4j
public class GlobalStateCheck {

    @Autowired
    private LicenseVerify licenseVerify;

    /**30分钟检测一次，不能太快也不能太慢*/
    @Scheduled(cron = "0 */30 * * * ?")
    @PostConstruct
    protected void updateGlobalState() throws Exception {
        if(licenseVerify.verify()){
            log.info("证书验证正常！");
            GlobalState.globalState = true;
        }else {
            log.info("证书验证失败！");
            GlobalState.globalState = false;
        }
    }
}
