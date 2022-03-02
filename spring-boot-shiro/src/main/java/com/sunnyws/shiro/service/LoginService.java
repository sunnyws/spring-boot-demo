package com.sunnyws.shiro.service;

import com.sunnyws.shiro.entity.SysUser;

/**
 * This class is for LoginService
 *
 * @author qinlang
 * @date 2021/12/27
 */
public interface LoginService {


    /**
    * This mentod is for  find User By   UserName
    * @date 2021/12/27
    **/
    SysUser findByName(String name);
}
