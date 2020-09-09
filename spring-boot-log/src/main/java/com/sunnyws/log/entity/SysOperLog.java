package com.sunnyws.log.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志记录表 oper_log
 * 
 * @author ruoyi
 */
@Data
public class SysOperLog implements Serializable {
    private static final long serialVersionUID = 1L;


    @TableId(value = "oper_id",type = IdType.UUID)
    private String operId;

    /** 操作模块 */
    private String title;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    private Integer businessType;

//    /** 业务类型数组 */
//    private Integer[] businessTypes;

    /** 请求方法 */
    private String method;

    /** 请求方式 */
    private String requestMethod;

    /** 操作类别 **/
    private String operatorType;

    /** 操作人员 */
    private String operName;

    /** 部门名称 */
    private String deptName;

    /** 请求url */
    private String operUrl;

    /** 操作地址 */
    private String operIp;

    /** 请求参数 */
    private String operParam;

    /** 返回参数 */
    private String jsonResult;

    /** 操作状态（0正常 1异常） */
    private Integer status;

    /** 错误消息 */
    private String errorMsg;

    /** 操作时间 */
    private Date operTime;
}
