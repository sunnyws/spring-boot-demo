package com.sunnyws.license.creator.service;

import cn.hutool.core.date.DateUtil;
import com.sunnyws.license.creator.exception.LicenseCreatorException;
import com.sunnyws.license.creator.param.LicenseCreatorParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>证书生成接口实现</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 11:43 下午 2020/8/21
 */
@Service
@Slf4j
public class LicenseCreatorService {


    /**
     * <p>生成证书</p>
     * @param param 证书创建需要的参数对象
     * @return Map<String,Object>
     */
    public boolean generateLicense(LicenseCreatorParam param){
        try {
            LicenseCreatorManager licenseCreator = new LicenseCreatorManager(param);
            Boolean flag = licenseCreator.generateLicense();
            if(flag){
                log.info("证书生成成功，证书有效期：{} - {}",DateUtil.formatDate((param.getIssuedTime())),DateUtil.formatDate(param.getExpiryTime()));
            }else{
                log.error("证书文件生成失败！");
            }
            return flag;
        }catch (Exception e){
            throw new LicenseCreatorException(e);
        }
    }



}
