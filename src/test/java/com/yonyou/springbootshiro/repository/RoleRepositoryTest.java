package com.yonyou.springbootshiro.repository;

import com.yonyou.springbootshiro.entity.SysRole;
import com.yonyou.springbootshiro.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-06 16:35
 */
class RoleRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;


    void  save(){
        SysUser user =new SysUser();
        user.setId(3l);
        user.setName("liubei");
        SysRole sysRole=new SysRole();


    }

}