package com.lwq.springsecurity.controller;

import com.lwq.springsecurity.dto.R;
import com.lwq.springsecurity.entity.SysUser;
import com.lwq.springsecurity.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "测试Controller")
@RestController
public class TestController {


    @ApiOperation("你好世界!")
    @GetMapping("/hello")
    public R hello() {
        System.out.println("我执行了");
        return R.ok("你好世界");
    }

    @ApiOperation("你好世界2!")
    @GetMapping("/hello2")
    public R hello2() {
        System.out.println("我执行了");
        return R.ok("你好世界2");
    }

    @PreAuthorize("hasAnyAuthority('hello3')")
    @ApiOperation("你好世界3!")
    @GetMapping("/hello3")
    public R hello3() {
        System.out.println("我执行了");
        return R.ok("你好世界3");
    }
}
