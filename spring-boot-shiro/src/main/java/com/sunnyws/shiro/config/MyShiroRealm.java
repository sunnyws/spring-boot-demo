package com.sunnyws.shiro.config;

/**
 * This class is for MyShiroRealm
 *
 * @author qinlang
 * @date 2021/12/27
 */

import com.sunnyws.shiro.entity.SysPermissions;
import com.sunnyws.shiro.entity.SysRole;
import com.sunnyws.shiro.entity.SysUser;
import com.sunnyws.shiro.service.LoginService;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * 实现AuthorizingRealm接口用户用户认证
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private LoginService loginService;

    /**
     * 身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken){
        // 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        // 获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        SysUser user = loginService.findByName(name);
        if (user == null) {
            throw new AccountException("未查询到用户！");
        } else {
            // 这里验证authenticationToken和simpleAuthenticationInfo的信息
            return new SimpleAuthenticationInfo(name, user.getPassword(), getName());
        }
    }

    /**
     * 角色权限和对应权限添加
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        // 查询用户名称
        SysUser user = loginService.findByName(name);
        // 添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (SysRole role : user.getSysRoles()) {
            // 添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (SysPermissions permission : role.getPermissions()) {
                // 添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPermissionsName());
            }
        }
        return simpleAuthorizationInfo;
    }

}