package com.sunnyws.license.creator.service;

import com.sunnyws.license.creator.param.LicenseCreatorParam;
import com.sunnyws.license.creator.param.CustomKeyStoreParam;
import de.schlichtherle.license.*;
import lombok.extern.slf4j.Slf4j;

import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.util.prefs.Preferences;

/**
 * - @Description:  LicenseCreatorManager
 * - @Author qinlang
 * - @Date 2021/8/18
 * - @Version 1.0
 **/
@Slf4j
public class LicenseCreatorManager {

    private final static X500Principal DEFAULT_HOLDER_AND_ISSUER = new X500Principal("CN=a, OU=a, O=a, L=a, ST=a, C=a");

    private LicenseCreatorParam param;

    public LicenseCreatorManager(LicenseCreatorParam param) {
        this.param = param;
    }

    /**
     * 生成License证书
     */
    public boolean generateLicense() throws Exception{
        LicenseManager licenseManager = new LicenseCustomManager(initLicenseParam());
        LicenseContent licenseContent = initLicenseContent();
        licenseManager.store(licenseContent, new File(param.getLicensePath()));
        return true;
    }

    /**
     * 初始化证书生成参数
     */
    private LicenseParam initLicenseParam() {
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);

        //设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam privateStoreParam = new CustomKeyStoreParam(LicenseCreator.class
                , param.getPrivateKeysStorePath()
                , param.getPrivateAlias()
                , param.getStorePass()
                , param.getKeyPass());

        return new DefaultLicenseParam(param.getSubject()
                , preferences
                , privateStoreParam
                , cipherParam);
    }

    /**
     * 设置证书生成正文信息
     */
    private LicenseContent initLicenseContent() {
        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setIssuer(DEFAULT_HOLDER_AND_ISSUER);

        licenseContent.setSubject(param.getSubject());
        licenseContent.setIssued(param.getIssuedTime());
        licenseContent.setNotBefore(param.getIssuedTime());
        licenseContent.setNotAfter(param.getExpiryTime());
        licenseContent.setConsumerType(param.getConsumerType());
        licenseContent.setConsumerAmount(param.getConsumerAmount());
        licenseContent.setInfo(param.getDescription());

        //扩展校验，这里可以自定义一些额外的校验信息(也可以用json字符串保存)
        if (param.getLicenseExtraParam() != null) {
            licenseContent.setExtra(param.getLicenseExtraParam());
        }

        return licenseContent;
    }
}

