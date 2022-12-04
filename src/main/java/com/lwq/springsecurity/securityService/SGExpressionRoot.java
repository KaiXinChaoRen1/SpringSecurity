package com.lwq.springsecurity.securityService;

import com.lwq.springsecurity.model.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限校验方法
 */

@Component("myEx")
public class SGExpressionRoot {

    public boolean myHasAuthority(String authority){
        //获取用户所有权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();
        return permissions.contains(authority);

    }

}
