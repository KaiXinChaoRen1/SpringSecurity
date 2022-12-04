package com.lwq.springsecurity.controller;

import com.lwq.springsecurity.dto.R;
import com.lwq.springsecurity.entity.SysUser;
import com.lwq.springsecurity.securityService.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api("登录Controller")
@RestController
public class LonginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation("登录")
    @PostMapping("/user/login")
    public R login(@RequestBody SysUser sysUser) throws Exception {

        return loginService.login(sysUser);
    }

    @ApiOperation("退出登录")
    @PostMapping("/user/logout")
    public R logout() throws Exception {

        return loginService.logout();
    }
}
