package com.lwq.springsecurity.securityService;

import com.lwq.springsecurity.utils.ResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义security处理'权限'异常,写好之后还需要配置给security
 */
@Component
public class AccessDeniedHandleImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseUtils.write(response,"你tm没权限啊乱点什么!?");
    }
}
