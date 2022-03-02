package com.sunnyws.shiro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * This class is for SysRole
 *
 * @author qinlang
 * @date 2021/12/27
 */

@Data
@AllArgsConstructor
public class SysRole {

    private Long id;
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private Set<SysPermissions> permissions;
}