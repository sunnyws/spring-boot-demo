package com.sunnyws.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
- @Description: 用户表
- @author generator
- @since 2020-07-10 11:26:07
*/
@Data
@TableName(value="sys_user")
public class UserEntity {


    /**
     * 主键id
     */
    @TableField(value = "id")
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type = IdType.UUID)
    private String id;


    /**
     * 登录账号
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "登录账号")
    private String username;


    /**
     * 真实姓名
     */
    @TableField(value = "realname")
    @ApiModelProperty(value = "真实姓名")
    private String realname;


    /**
     * 密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value = "密码")
    private String password;


    /**
     * md5密码盐
     */
    @TableField(value = "salt")
    @ApiModelProperty(value = "md5密码盐")
    private String salt;


    /**
     * 头像
     */
    @TableField(value = "avatar")
    @ApiModelProperty(value = "头像")
    private String avatar;


    /**
     * 生日
     */
    @TableField(value = "birthday")
    @ApiModelProperty(value = "生日")
    private Date birthday;


    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    @TableField(value = "sex")
    @ApiModelProperty(value = "性别(0-默认未知,1-男,2-女)")
    private Integer sex;


    /**
     * 电子邮件
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "电子邮件")
    private String email;


    /**
     * 电话
     */
    @TableField(value = "phone")
    @ApiModelProperty(value = "电话")
    private String phone;


    /**
     * 机构编码
     */
    @TableField(value = "org_code")
    @ApiModelProperty(value = "机构编码")
    private String orgCode;


    /**
     * 状态(1-正常,2-冻结)
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态(1-正常,2-冻结)")
    private Integer status;


    /**
     * 删除状态(0-正常,1-已删除)
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "删除状态(0-正常,1-已删除)")
    private Integer delFlag;


    /**
     * 第三方登录的唯一标识
     */
    @TableField(value = "third_id")
    @ApiModelProperty(value = "第三方登录的唯一标识")
    private String thirdId;


    /**
     * 第三方类型
     */
    @TableField(value = "third_type")
    @ApiModelProperty(value = "第三方类型")
    private String thirdType;


    /**
     * 同步工作流引擎(1-同步,0-不同步)
     */
    @TableField(value = "activiti_sync")
    @ApiModelProperty(value = "同步工作流引擎(1-同步,0-不同步)")
    private Integer activitiSync;


    /**
     * 工号，唯一键
     */
    @TableField(value = "work_no")
    @ApiModelProperty(value = "工号，唯一键")
    private String workNo;


    /**
     * 职务，关联职务表
     */
    @TableField(value = "post")
    @ApiModelProperty(value = "职务，关联职务表")
    private String post;


    /**
     * 座机号
     */
    @TableField(value = "telephone")
    @ApiModelProperty(value = "座机号")
    private String telephone;


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
     * 身份（1普通成员 2上级）
     */
    @TableField(value = "user_identity")
    @ApiModelProperty(value = "身份（1普通成员 2上级）")
    private Integer userIdentity;


    /**
     * 负责部门
     */
    @TableField(value = "depart_ids")
    @ApiModelProperty(value = "负责部门")
    private String departIds;


}