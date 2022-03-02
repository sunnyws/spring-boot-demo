package com.sunnyws.log.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunnyws.log.annotation.Log;
import com.sunnyws.log.entity.Result;
import com.sunnyws.log.entity.SysOperLog;
import com.sunnyws.log.entity.UserEntity;
import com.sunnyws.log.enums.BusinessType;
import com.sunnyws.log.service.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * - @description:  SysOperLogController
 * - @author qinlang
 * - @date 2020/9/7 9:32
 */


@Slf4j
@Api(tags = "日志表")
@RestController
@RequestMapping("/operLog")
public class SysOperLogController {

    @Autowired
    private SysOperLogService sysOperLogService;

    /**
     * 分页列表查询
     * @param user
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "日志表-分页列表查询", notes = "日志表-分页列表查询")
    @GetMapping(value = "/list")
    @Log(title = "日志查询",businessType = BusinessType.OTHER,isSaveReponseData = false)
    public Result<IPage<SysOperLog>> queryPageList(UserEntity user,
                                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Result<IPage<SysOperLog>> result = new Result<IPage<SysOperLog>>();
        QueryWrapper<SysOperLog> queryWrapper = new QueryWrapper<SysOperLog>();
        Page<SysOperLog> page = new Page<SysOperLog>(pageNo, pageSize);
        IPage<SysOperLog> pageList = sysOperLogService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    /**
     * 通过id查询
     * @param id
     * @return
     */
    @ApiOperation(value = "日志表-通过id查询", notes = "日志表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<SysOperLog> queryById(@RequestParam(name="id",required=true) String id) {
        Result<SysOperLog> result = new Result<SysOperLog>();
        SysOperLog operLog = sysOperLogService.getById(id);
        if(operLog==null) {
            result.error500("未找到对应实体");
        }else {
            result.setResult(operLog);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     *   添加
     * @param operLog
     * @return
     */
    @ApiOperation(value = "日志表- 添加", notes = "日志表- 添加")
    @PostMapping(value = "/add")
    public Result<SysOperLog> add(@RequestBody SysOperLog operLog) {
        Result<SysOperLog> result = new Result<SysOperLog>();
        try {
            sysOperLogService.save(operLog);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @Log(title = "删除日志", businessType = BusinessType.DELETE)
    @ApiOperation(value = "日志表-通过id删除", notes = "日志表- 通过id删除")
    @PostMapping(value = "/delete")
    public Result<SysOperLog> delete(@RequestParam(name="id",required=true) String id) {
        Result<SysOperLog> result = new Result<SysOperLog>();
        SysOperLog operLog = sysOperLogService.getById(id);
        if(operLog==null) {
            result.error500("未找到对应实体");
        }else {
            boolean ok =  sysOperLogService.removeById(id);
            if(ok) {
                result.success("删除成功!");
            }
        }

        return result;
    }
    /**
     *  批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "日志表-批量删除", notes = "日志表- 批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<SysOperLog> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        Result<SysOperLog> result = new Result<SysOperLog>();
        if(ids==null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        }else {
            this. sysOperLogService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }
}
