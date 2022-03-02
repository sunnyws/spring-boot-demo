package com.sunnyws.shiro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class is for SysPermissions
 *
 * @author qinlang
 * @date 2021/12/27
 */

@Data
@AllArgsConstructor
public class SysPermissions {
    private Long id;
    private String permissionsName;
}