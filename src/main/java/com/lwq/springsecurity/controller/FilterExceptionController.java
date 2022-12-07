package com.lwq.springsecurity.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 本项目中专门处理过滤器中发生的异常,获取异常并抛出由全局异常处理器处理,返回前端统一的数据
 */

@RestController
public class FilterExceptionController {

    @RequestMapping("/filterExceptionHandler")
    public void name1(HttpServletRequest request) throws Exception {
        if (request.getAttribute("exception") instanceof Exception) {
            throw ((Exception) request.getAttribute("exception"));
        }
    }

}
