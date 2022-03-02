package com.sunnyws.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunnyws.log.entity.SysOperLog;
import com.sunnyws.log.service.SysOperLogService;
import com.sunnyws.log.mapper.SysOperLogMapper;
import org.springframework.stereotype.Service;

/**
 * - @description:  SysOperLogServiceImpl
 * - @author qinlang
 * - @date 2020/9/7 8:56
 */

@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {
}
