package com.sunnyws.license.verify.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * - @Description:  CommonUtils
 * - @Author qinlang
 * - @Date 2021/8/19
 * - @Version 1.0
 **/

@Slf4j
public class CommonUtils {

    //封装格外验证对象
    public static Map<String, Object> getLicenseExtraParam(String cpuSerial){
        Map<String, Object> licenseExtraParam = new HashMap<String, Object>();
        licenseExtraParam.put("cpuSerial",cpuSerial);
        return  licenseExtraParam;
    }
    public static Map<String, Object> getLicenseExtraParam(){
        Map<String, Object> licenseExtraParam = new HashMap<String, Object>();
        licenseExtraParam.put("cpuSerial",getCpuSerial());
        return  licenseExtraParam;
    }



    public static String getCpuSerial(){
        String os = getOSName();
        String cpuSerial = null;
//        log.info("当前操作系统为：{}",os);
        if(os.contains("windows")){
            cpuSerial = getCPUID_Windows();
        }else if (os.contains("linux")){
            cpuSerial = getCPUID_linux();
        }
        if(StrUtil.isEmpty(cpuSerial)){
            log.error("获取cpu序列号失败！");
            return null;
        }
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String digestHex = md5.digestHex(cpuSerial);
        return digestHex;
    }

    /**
     * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
     */
    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }


    public static void main(String[] args) {
        getCpuSerial();
    }

    public static String getCPUID_Windows() {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_Processor\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.ProcessorId \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            // + "    exit for  \r\n" + "Next";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
            file.delete();
        } catch (Exception e) {
            log.error("获取cpu信息错误",e);
        }
        return result.trim();
    }

    public static String getCPUID_linux() {
        String result = "";
        String CPU_ID_CMD = "dmidecode";
        BufferedReader bufferedReader = null;
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{ "sh", "-c", CPU_ID_CMD });// 管道
            bufferedReader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[hwaddr]
                index = line.toLowerCase().indexOf("uuid");
                if (index >= 0) {// 找到了
                    // 取出mac地址并去除2边空格
                    result = line.substring(index + "uuid".length() + 1).trim();
                    break;
                }
            }

        } catch (IOException e) {
            log.error("获取cpu信息错误",e);
        }
        return result.trim();
    }
}
