package com.sunnyws.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunnyws.sharding.entity.YbdsCameraFaceRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 人脸抓拍记录(抓拍记录)(YbdsCameraFaceRecord)表数据库访问层
 *
 * @author qinlang
 * @since 2021-06-24 20:38:18
 */
@Mapper
public interface YbdsCameraFaceRecordMapper extends BaseMapper<YbdsCameraFaceRecord> {

    Page<YbdsCameraFaceRecord> getDate(Page<YbdsCameraFaceRecord> page, @Param("snapTime")String snapTime);

    Page<YbdsCameraFaceRecord> getPageByTime(Page<YbdsCameraFaceRecord> page, @Param("startTime") Date startTime, @Param("endTime")Date endTime) ;

    String getMaxTime() ;
}