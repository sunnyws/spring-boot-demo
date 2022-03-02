package com.sunnyws.license.verify.config;


import com.sunnyws.license.verify.param.LicenseVerifyParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableConfigurationProperties(LicenseVerifyParam.class)
public class LicenseConfig {


    @Bean(initMethod = "installLicense", destroyMethod = "unInstallLicense")
    public LicenseVerify licenseVerify(LicenseVerifyParam param) {
        return new LicenseVerify(param.getSubject(),param.getPublicAlias(), param.getStorePass(),param.getLicensePath(), param.getPublicKeysStorePath());
    }


}
