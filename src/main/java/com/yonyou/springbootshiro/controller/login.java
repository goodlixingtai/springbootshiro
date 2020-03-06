package com.yonyou.springbootshiro.controller;

import com.yonyou.springbootshiro.entity.SysUser;
import com.yonyou.springbootshiro.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-06 19:07
 */
@RestController

public class login {
    @Autowired
    LoginService loginService;
    /**
     * POST登录
     * @param map
     * @return
     */
    @PostMapping(value = "/login")
    public String login(@RequestBody SysUser user) {
        // 添加用户认证信息
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getName(), user.getPassword());
        // 进行验证，这里可以捕获异常，然后返回对应信息
        SecurityUtils.getSubject().login(usernamePasswordToken);
        return "login ok!";
    }
}
