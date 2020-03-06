package com.yonyou.springbootshiro.service;

import com.yonyou.springbootshiro.entity.SysRole;
import com.yonyou.springbootshiro.entity.SysUser;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-06 15:30
 */
public interface LoginService  {
    SysUser addUser(SysUser user);

    SysRole addRole(SysRole role);

    SysUser findByName(String name);
}
