package com.sunnyws.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
- @Description: 
- @author generator
- @since 2020-07-10 11:22:05
*/
@Data
@TableName(value="sys_dict")
public class DictEntity {


    /**
     * 
     */
    @TableField(value = "id")
    @ApiModelProperty(value = "")
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;


    /**
     * 字典名称
     */
    @TableField(value = "dict_name")
    @ApiModelProperty(value = "字典名称")
    private String dictName;


    /**
     * 字典编码
     */
    @TableField(value = "dict_code")
    @ApiModelProperty(value = "字典编码")
    private String dictCode;


    /**
     * 描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value = "描述")
    private String description;


    /**
     * 删除状态
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "删除状态")
    private Integer delFlag;


    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建人")
    private String createBy;


    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    /**
     * 更新人
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "更新人")
    private String updateBy;


    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


    /**
     * 字典类型0为string,1为number
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "字典类型0为string,1为number")
    private Integer type;


}