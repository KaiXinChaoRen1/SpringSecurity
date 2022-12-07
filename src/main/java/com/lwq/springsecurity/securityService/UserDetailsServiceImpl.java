package com.lwq.springsecurity.securityService;

import com.lwq.springsecurity.dao.SysUserRepository;
import com.lwq.springsecurity.entity.SysUser;
import com.lwq.springsecurity.model.LoginUser;
import com.lwq.springsecurity.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 手动实现UserDetailsService
 *      逻辑就是根据username获取用户的所有信息
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //1.查询用户信息
        SysUser sysUser= sysUserRepository.findByUserName(username);
        if(sysUser==null){
            throw new RuntimeException("用户不存在");
        }

        //2.TODO查询权限信息
        List<String> userAuthorities = sysUserService.getUserAuthorities(sysUser.getId());

        //3.封装成UserDetails返回
        LoginUser loginUser = new LoginUser(sysUser, userAuthorities);
        return loginUser;
    }
}
