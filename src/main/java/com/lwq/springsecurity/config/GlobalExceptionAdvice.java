package com.lwq.springsecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 通用异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {


    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e) {
        log.error(e.toString(), e);
        return "全局异常捕获RuntimeException,服务器出现错误啦~~--->" + e.getMessage();
    }

    //下面两个是我自己加的
    //注意异常类别引错
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.toString(), e);
        return "全局异常捕获,权限异常,你tm没有权限";
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String handleAccessDeniedException(BadCredentialsException e) {
        log.error(e.toString(), e);
        return "全局异常捕获,登录失败,密码不对";
    }


}
