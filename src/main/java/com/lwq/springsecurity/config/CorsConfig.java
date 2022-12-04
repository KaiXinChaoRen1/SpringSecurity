package com.lwq.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * springboot配置跨域,(security还需要再配)(微服务的话,网关配置还不太一样好像)
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings (CorsRegistry registry) {
        //允许跨域的路径
        registry.addMapping("/**")
                //允许跨域请求的域名?
                .allowedOriginPatterns("*")
                //允许cookie
                .allowCredentials(true)
                //请求方式
                .allowedMethods("GET","POST","PUT","DELETE")
                //允许的header属性
                .allowedHeaders("*")
                //允许跨域验证时间
                .maxAge(3600);
    }
}
