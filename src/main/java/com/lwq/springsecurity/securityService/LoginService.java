package com.lwq.springsecurity.securityService;

import com.lwq.springsecurity.dto.R;
import com.lwq.springsecurity.entity.SysUser;
import com.lwq.springsecurity.model.LoginUser;
import com.lwq.springsecurity.utils.JwtHelper;
import com.lwq.springsecurity.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 真正的登录逻辑
     */
    public R login(SysUser sysUser) throws Exception {
        //委托AuthenticationManager进行用户认证
        //封装数据
        String username = sysUser.getUserName();
        String password = sysUser.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        //获取AuthenticationManager
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        //认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //失败则提示(在我观察,密码输错了会在前面就报错)
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("出错啦,来控制台看看");
        }
        //成功则生成jwt
        LoginUser correctUser = (LoginUser) authentication.getPrincipal();
        long id = correctUser.getSysUser().getId();
        String token = JwtHelper.createToken(id);

        //将用户数据保存到redis中
        redisUtils.set("login:" + String.valueOf(id), correctUser, 3l, TimeUnit.DAYS);

        //返回数据
        return R.ok(token);
    }

    public R logout() {
        //获取id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getSysUser().getId();
        //删除redis中数据
        Boolean delete = redisUtils.delete("login:" + String.valueOf(id));
        if (delete) {
            return R.ok("删除成功");
        } else {
            return R.fail("删除失败");
        }
    }
}
