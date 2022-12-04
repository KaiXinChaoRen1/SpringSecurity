package com.lwq.springsecurity.filter;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.lwq.springsecurity.model.LoginUser;
import com.lwq.springsecurity.utils.JwtHelper;
import com.lwq.springsecurity.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 所有请求校验的过滤器,?
 *      写完之后还要配置一下在整个security过滤器链里的顺序
 */

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private  RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if( token== null || "".equals(token)){
            //放行,
            filterChain.doFilter(request,response);
            return;  //不return的话后面的过滤器执行完还会在继续执行后面的
        }
        //解析token
        Long userId = JwtHelper.getUserId(token);
        if(userId==null){
            //使用security提供的认证/授权错误处理(不好用,还会跟全局异常冲突)
            //throw new RuntimeException("token令牌有错误");

            RuntimeException e = new RuntimeException("token令牌有错误");
            //处理filter的异常
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/filterExceptionHandler").forward(request, response);
        }
        //redis中获取用户信息
        String json = redisUtils.get("login:"+String.valueOf(userId));
        if( json== null || "".equals(json)){
            //使用security提供的认证/授权错误处理(不好用,还会跟全局异常冲突)
            //throw new RuntimeException("用户登录已过期,请重新登陆");

            RuntimeException e = new RuntimeException("用户登录已过期,请重新登陆");
            //处理filter的异常
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/filterExceptionHandler").forward(request, response);
        }
        LoginUser loginUser = JSON.parseObject(json, LoginUser.class);



        //封装authenticationToken(使用三个参数这个方法,第三个参数是权限信息,内部会设置这个对象是已经认证过的)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        //存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
        filterChain.doFilter(request,response);
    }
}
