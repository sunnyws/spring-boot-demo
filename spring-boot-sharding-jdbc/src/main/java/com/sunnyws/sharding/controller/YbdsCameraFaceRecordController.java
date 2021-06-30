package com.sunnyws.sharding.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunnyws.sharding.entity.YbdsCameraFaceRecord;
import com.sunnyws.sharding.mapper.YbdsCameraFaceRecordMapper;
import com.sunnyws.sharding.service.YbdsCameraFaceRecordService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description 人脸抓拍记录(抓拍记录)(YbdsCameraFaceRecord)表控制层
 * @Author qinlang
 * @Date 2021-06-24 20:38:18
 */
@RestController
@RequestMapping("/ybdsCameraFaceRecord")
public class YbdsCameraFaceRecordController {
    /**
     * 服务对象
     */
    @Resource
    private YbdsCameraFaceRecordMapper ybdsCameraFaceRecordMapper;

    /**
     * 分页查询所有数据
     *
     * @param page                 分页对象
     * @param ybdsCameraFaceRecord 查询实体
     * @return 所有数据
     */
    @GetMapping("page")
    public Object page(Page<YbdsCameraFaceRecord> page, YbdsCameraFaceRecord ybdsCameraFaceRecord) {
        return ybdsCameraFaceRecordMapper.getDate(page, DateUtil.format(ybdsCameraFaceRecord.getSnapTime(),"yyyy-MM-dd HH:mm:ss"));
    }

    @GetMapping("getPageByTime")
    public Object getPageByTime(Page<YbdsCameraFaceRecord> page,
                                @RequestParam(value = "startTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date startTime,
                                @RequestParam(value = "endTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date endTime) {
        return ybdsCameraFaceRecordMapper.getPageByTime(page, startTime,endTime);
    }

    @GetMapping("getMaxTime")
    public Object getDataByMaxTime() {
        return ybdsCameraFaceRecordMapper.getMaxTime();
    }


    @PostMapping("insert")
    public void insert(@RequestBody YbdsCameraFaceRecord record){
        ybdsCameraFaceRecordMapper.insert(record);
    }

}