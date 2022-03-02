package com.sunnyws.shiro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * This class is for SysUser
 *
 * @author qinlang
 * @date 2021/12/27
 */
@Data
@AllArgsConstructor
public class SysUser {
    private Long id;
    private String userName;
    private String password;
    /**
     * 用户对应的角色集合
     */
    private Set<SysRole> sysRoles;
}
