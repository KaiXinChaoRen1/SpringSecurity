package com.lwq.springsecurity;

import cn.hutool.core.codec.BCD;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.lwq.springsecurity.dao.SysUserRepository;
import com.lwq.springsecurity.entity.SysUser;
import com.lwq.springsecurity.model.LoginUser;
import com.lwq.springsecurity.service.SysUserService;
import com.lwq.springsecurity.utils.IdWorker;
import com.lwq.springsecurity.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringSecurityApplicationTests {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private  SysUserService sysUserService;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 测试redis存储LoginUser
     */
    @Test
    void name8() {

        Optional<SysUser> byId = sysUserRepository.findById(1599288844300193792l);
        SysUser user = byId.get();
        System.out.println(JSON.toJSONString(user));
        redisUtils.set("hehe",user,3l, TimeUnit.DAYS);
    }

    /**
     * 测试获取用户对应的所有权限
     */
    @Test
    void name7() {
        List<String> userAuthorities = sysUserService.getUserAuthorities(1599288844300193792l);
        System.out.println(userAuthorities);
    }

    /**
     * 测试hutoool雪花算法
     */
    @Test
    void name6() {
        HashSet<Long> longs = new HashSet<Long>();
        for (int i = 0; i < 10000000; i++) {
            longs.add(IdWorker.newId());
        }
        System.out.println(longs.size());
    }


    /**
     * 测试BCryptPasswordEncoder
     * 因为随机盐不同,因此即使相同的明文,密文也不同
     */
    @Test
    void name5() {
        //测试加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("1234");
        String encode2 = passwordEncoder.encode("1234");
        System.out.println(encode);
        System.out.println(encode2);
        //测试校验
        System.out.println(passwordEncoder.matches("1234","$2a$10$FsQd7cSuM4bV9giDW1rhOOwr33OAXgTXlZ/HDXLlEb.iLK4Knu4du"));

    }


    /**
     * 测试非空username
     */
    @Test
    void name4() {
        SysUser root = SysUser.builder().id(IdWorker.newId()).password("root").nickName("可爱多").build();
        sysUserRepository.save(root);
    }

    /**
     * 测试唯一username
     */
    @Test
    void name3() {
        SysUser root = SysUser.builder().id(IdWorker.newId()).userName("root").password("root").nickName("可爱多").build();
        SysUser root2 = SysUser.builder().id(IdWorker.newId()).userName("root").password("root").nickName("可爱少").build();
        sysUserRepository.save(root);
        sysUserRepository.save(root2);
    }

    /**
     * 测试保存用户
     */
    @Test
    void name1() {
        SysUser root = SysUser.builder().id(IdWorker.newId()).userName("root").password("root").nickName("风华龚总").build();
        sysUserRepository.save(root);
    }

    /**
     * 根据名字查询用户,利用jpa拼接方法名的查询
     */
    @Test
    void name2() {
        SysUser user= sysUserRepository.findByUserName("root");
        System.out.println(user);
    }



}
