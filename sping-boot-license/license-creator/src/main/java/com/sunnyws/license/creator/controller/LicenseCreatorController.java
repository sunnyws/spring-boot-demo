package com.sunnyws.license.creator.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sunnyws.license.creator.exception.LicenseCreatorException;
import com.sunnyws.license.creator.param.LicenseCreatorParam;
import com.sunnyws.license.creator.service.LicenseCreatorService;
import com.sunnyws.license.creator.utils.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Date;

@Api(tags = "授权接口")
@Slf4j
@RestController
public class LicenseCreatorController {

    @Value("${server.port}")
    private int serverPort;


    @Value("${license.licensePath}")
    private String licenseUrl;

    @Autowired
    private LicenseCreatorService creatorService;

    @Resource
    private LicenseCreatorParam licenseCreatorParam;


    @PostMapping("/generate")
    @ApiOperation(value = "申请授权", notes = "申请授权")
    public JSONObject generate(@RequestParam("expiryTime")
                               @ApiParam("过期时间,时间格式：2021-08-01 00:00:00") Date expiryTime,
                               @RequestParam("cpuSerial")
                               @ApiParam("机器码") String cpuSerial){
        //开始时间
        licenseCreatorParam.setIssuedTime(new Date());
        //过期时间
        licenseCreatorParam.setExpiryTime(expiryTime);
        licenseCreatorParam.setPrivateKeysStorePath(licenseCreatorParam.getPrivateKeysStorePath());
        //cpu序列号
        licenseCreatorParam.setLicenseExtraParam(CommonUtils.getLicenseExtraParam(cpuSerial));
        // 根据时间戳，命名lic文件
        String licDir = String.format("%1$s/%2$s/",
                licenseUrl,
                DateUtil.format(new Date(), "yyyyMMddHHmmss"));//相对路径
        File file = new File(licDir);
        if(!file.exists()){
            if(!file.mkdirs()){
                throw new LicenseCreatorException("创建目录"+licDir+",失败，请检查是是否有创建目录的权限或者手动进行创建！");
            }
        }
        licenseCreatorParam.setLicensePath(licDir + "license.lic");
        //拼接下载路径
        String licUrl = "";
        try {
            InetAddress  address = InetAddress.getLocalHost();
            licUrl =  "http://" + address.getHostAddress()+ ":" + serverPort  + "/";
        }catch (UnknownHostException e){
            throw new LicenseCreatorException("生成下载路径失败！");
        }
        licenseCreatorParam.setLicUrl(licUrl+"download?path="+URLUtil.encode(licenseCreatorParam.getLicensePath()));
        //生成下载证书
        creatorService.generateLicense(licenseCreatorParam);
        return JSONUtil.parseObj(licenseCreatorParam);
    }

    @GetMapping("/download")
    @ApiOperation(value = "下载证书", notes = "下载证书")
    public void downLoad(@RequestParam(value = "path") @ApiParam("下载地址") String path, HttpServletRequest request, HttpServletResponse response) throws Exception{
        File file = new File(path);
        if(!file.exists()){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        InputStream is = new FileInputStream(file);
        String fileName = file.getName();
        // 设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        // 设置编码格式
        response.setCharacterEncoding("UTF-8");
        // 设置可以识别Html文件
        response.setContentType("text/html");
        // 设置头中附件文件名的编码
        setAttachmentCoding(request, response, fileName);
        // 设置文件头：最后一个参数是设置下载文件名
//        response.setHeader("Content-Disposition", "attachment;fileName="+file.getName()+".lic");
        BufferedInputStream bis = new BufferedInputStream(is);
        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024 * 10];
        int length ;
        while ((length = bis.read(buffer, 0, buffer.length)) != -1) {
            os.write(buffer, 0, length);
        }
        os.close();
        bis.close();
        is.close();
    }

    private void setAttachmentCoding(HttpServletRequest request, HttpServletResponse response, String fileName) {
        String browser;
        try {
            browser = request.getHeader("User-Agent");
            if (-1 < browser.indexOf("MSIE 6.0") || -1 < browser.indexOf("MSIE 7.0")) {
                // IE6, IE7 浏览器
                response.addHeader("content-disposition", "attachment;filename="
                        + new String(fileName.getBytes(), "ISO8859-1"));
            } else if (-1 < browser.indexOf("MSIE 8.0")) {
                // IE8
                response.addHeader("content-disposition", "attachment;filename="
                        + URLEncoder.encode(fileName, "UTF-8"));
            } else if (-1 < browser.indexOf("MSIE 9.0")) {
                // IE9
                response.addHeader("content-disposition", "attachment;filename="
                        + URLEncoder.encode(fileName, "UTF-8"));
            } else if (-1 < browser.indexOf("Chrome")) {
                // 谷歌
                response.addHeader("content-disposition",
                        "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
            } else if (-1 < browser.indexOf("Safari")) {
                // 苹果
                response.addHeader("content-disposition", "attachment;filename="
                        + new String(fileName.getBytes(), "ISO8859-1"));
            } else {
                // 火狐或者其他的浏览器
                response.addHeader("content-disposition",
                        "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
            }
        } catch (Exception e) {
            log.error("文件下载异常",e);
        }
    }

    @GetMapping("test")
    @ApiOperation(value = "测试", notes = "测试")
    public void test(){
        throw new LicenseCreatorException("生成下载路径失败！");
    }

}