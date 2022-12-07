package com.lwq.springsecurity.config;

import com.lwq.springsecurity.filter.OncePerJWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * security核心配置
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限控制注解
public class WebSecurityConfig {

    @Autowired
    private OncePerJWTFilter jwtFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    //p22有详解
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //把过滤器添加到某个过滤器之前(我们的需要注入)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                //关闭防止csrf攻击,因为前后端分离使用token这种方式不惧csrf攻击,6
                .csrf().disable()
                //禁止通过session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //未登录可以访问,登录不能访问
                .antMatchers("/user/login").anonymous()
                //permitAll()全都可以访问
                .antMatchers("/hello").permitAll()
                //任意用户,认证之后都可以访问(学了授权之后再改)
                .anyRequest()
                .authenticated();

        //配置security的异常处理
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        //允许跨域
        http.cors();

        return http.build();
    }


    /**
     * 设置passwordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 放行swagger
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers(
                "/favicon.ico",
                "/swagger-resources/**",
                "/webjars/**",
                "/v3/**",
                "/swagger-ui.html/**",
                "/doc.html",
                "/hello2");//这里和上边的放行有啥区别?
    }


}
