package com.yonyou.springbootshiro.service.imp;

import com.yonyou.springbootshiro.entity.SysRole;
import com.yonyou.springbootshiro.entity.SysUser;
import com.yonyou.springbootshiro.repository.UserRepository;
import com.yonyou.springbootshiro.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-06 15:33
 */
@Service
public class LoginServiceImp implements LoginService {
    @Autowired
    UserRepository userRepository;
    //添加用户
    @Override
    public SysUser addUser(SysUser user) {

        return  userRepository.save(user);
    }
    //添加角色
    @Override
    public SysRole addRole(SysRole role) {

        return null;
    }

    @Override
    public SysUser findByName(String name) {
        return userRepository.findByName(name);
    }
}
