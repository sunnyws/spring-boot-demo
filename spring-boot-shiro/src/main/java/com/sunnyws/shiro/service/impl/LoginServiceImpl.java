package com.sunnyws.shiro.service.impl;

import com.sunnyws.shiro.entity.SysPermissions;
import com.sunnyws.shiro.entity.SysRole;
import com.sunnyws.shiro.entity.SysUser;
import com.sunnyws.shiro.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class is for LoginServiceImpl
 */


@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public SysUser findByName(String name) {
        return getMapByName(name);
    }

    /**
     * 模拟数据库查询
     *
     * @param userName 用户名
     * @return User
     */
    private SysUser getMapByName(String userName) {
        SysPermissions permissions1 = new SysPermissions(1L,"queryUser");
        SysPermissions permissions2 = new SysPermissions(2L,"addUser");
        Set<SysPermissions> adminPermissionsSet = new HashSet<>();
        adminPermissionsSet.add(permissions1);
        adminPermissionsSet.add(permissions2);

        SysRole adminRole = new SysRole(1L, "admin", adminPermissionsSet);
        Set<SysRole> roleSet = new HashSet<>();
        roleSet.add(adminRole);
        SysUser adminUser = new SysUser(1L, "admin", "123456", roleSet);


        Set<SysPermissions> gusetPermissionsSet = new HashSet<>();
        gusetPermissionsSet.add(permissions1);
        SysRole guestRole = new SysRole(2L, "guest", gusetPermissionsSet);
        Set<SysRole> gusetRoleSet = new HashSet<>();
        gusetRoleSet.add(guestRole);
        SysUser guestUser = new SysUser(2L, "guest", "123456", gusetRoleSet);

        Map<String, SysUser> map = new HashMap<>();
        map.put(adminUser.getUserName(), adminUser);
        map.put(guestUser.getUserName(), guestUser);
        return map.get(userName);
    }
}
