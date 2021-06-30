package com.sunnyws.sharding.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author qinlang
 * @since 2021-06-24 20:38:13
 */
@Data
@ApiModel(value = "人脸抓拍记录(抓拍记录)")
public class YbdsCameraFaceRecord extends Model {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "小区编码")
    @TableField("area_no")
    private String areaNo;

    @ApiModelProperty(value = "抓拍大图base64编码")
    @TableField("snap_big_image")
    private String snapBigImage;

    @ApiModelProperty(value = "抓拍机编码")
    @TableField("snap_no")
    private String snapNo;

    @ApiModelProperty(value = "抓拍时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField("snap_time")
    private Date snapTime;


    @ApiModelProperty(value = "抓拍时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField("snap_time")
    private Date startTime;

    @ApiModelProperty(value = "抓拍时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField()
    private Date endTime;

    @ApiModelProperty(value = "抓拍小图base64编码")
    @TableField("snap_small_image")
    private String snapSmallImage;

    @ApiModelProperty(value = "证件号码")
    @TableField("card_no")
    private String cardNo;

    @ApiModelProperty(value = "是否成功推送 0-未推送 1-推送成功 2-推送失败")
    @TableField("is_push")
    private String isPush;

    @ApiModelProperty(value = "推送失败原因")
    @TableField("error_msg")
    private String errorMsg;
}