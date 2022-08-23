package com.sunnyws.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
- @Description: 
- @author generator
- @since 2020-07-10 11:22:59
*/
@Data
@TableName(value="sys_dict_item")
public class DictItemEntity {


    /**
     * 
     */
    @TableField(value = "id")
    @ApiModelProperty(value = "")
    private String id;


    /**
     * 字典id
     */
    @TableField(value = "dict_id")
    @ApiModelProperty(value = "字典id")
    private String dictId;


    /**
     * 字典项文本
     */
    @TableField(value = "item_text")
    @ApiModelProperty(value = "字典项文本")
    private String itemText;


    /**
     * 字典项值
     */
    @TableField(value = "item_value")
    @ApiModelProperty(value = "字典项值")
    private String itemValue;


    /**
     * 描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value = "描述")
    private String description;


    /**
     * 排序
     */
    @TableField(value = "sort_order")
    @ApiModelProperty(value = "排序")
    private Integer sortOrder;


    /**
     * 状态（1启用 0不启用）
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态（1启用 0不启用）")
    private Integer status;


    /**
     * 
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "")
    private String createBy;


    /**
     * 
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "")
    private Date createTime;


    /**
     * 
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "")
    private String updateBy;


    /**
     * 
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "")
    private Date updateTime;


}