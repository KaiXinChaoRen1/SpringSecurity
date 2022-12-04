package com.lwq.springsecurity.controller;

import com.lwq.springsecurity.dto.R;
import com.lwq.springsecurity.entity.SysUser;
import com.lwq.springsecurity.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户Controller")
@RestController
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("根据id查询用户")
    @GetMapping("/getUserById")
    public R getUserById(long id) {
        return sysUserService.getById(id);
    }

    @ApiOperation("添加一个用户")
    @PostMapping("/addUser")
    public R addUser(@RequestBody SysUser sysUser) {
        return sysUserService.addUser(sysUser);
    }

    @ApiOperation("获取所有用户")
    @PostMapping("/getAllUser")
    public R addUser() {
        return sysUserService.getAllUser();
    }
}
