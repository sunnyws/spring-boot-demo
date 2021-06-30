package com.sunnyws.excel.controller;

import com.alibaba.excel.EasyExcel;
import com.sunnyws.excel.entity.UserEntity;
import com.sunnyws.excel.listener.UserListener;
import com.sunnyws.excel.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * - @Description:  TestController
 * - @Author qinlang
 * - @Date 2021/6/16
 * - @Version 1.0
 **/

@RestController
public class TestController {


    @Resource
    private UserService userService;


    // 导出
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        //   Student.class 是按导出类  data()应为数据库查询数据，这里只是模拟
        EasyExcel.write(response.getOutputStream(), UserEntity.class).sheet("模板").doWrite(userService.list());
    }

    /**
     * 读取 excel
     * @return
     */
    @RequestMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        //写法一
        // sheet里面可以传参 根据sheet下标读取或者根据名字读取 不传默认读取第一个
        EasyExcel.read(file.getInputStream(), UserEntity.class, new UserListener(userService)).sheet().doRead();
        // 写法2：
        /*ExcelReader excelReader = EasyExcel.read(file.getInputStream(), Student.class, new StudentListener(studentDao)).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();*/
        return "success";
    }
}
