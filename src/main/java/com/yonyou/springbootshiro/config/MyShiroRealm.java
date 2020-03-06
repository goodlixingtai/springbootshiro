package com.yonyou.springbootshiro.config;

import com.yonyou.springbootshiro.entity.Permission;
import com.yonyou.springbootshiro.entity.SysRole;
import com.yonyou.springbootshiro.entity.SysUser;
import com.yonyou.springbootshiro.service.LoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-06 19:32
 * 实现AuthorizingRealm接口用户用户认证
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;


    //授权信息
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
            for (Permission permission : role.getPermissions()) {
                // 添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return simpleAuthorizationInfo;
    }

    //认证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String naem=authenticationToken.getPrincipal().toString();
        // 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (naem == null) {
            return null;
        }

        String name = authenticationToken.getPrincipal().toString();
        SysUser user = loginService.findByName(name);
        if (user == null) {
            // 这里返回后会报出对应异常
            return null;
        } else {
            // 这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name,
                    user.getPassword().toString(), getName());
            return simpleAuthenticationInfo;
        }
    }
}
