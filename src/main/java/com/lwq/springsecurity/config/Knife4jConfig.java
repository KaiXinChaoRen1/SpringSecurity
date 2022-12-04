package com.lwq.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://localhost:8080/doc.html
 */
@Configuration
@EnableSwagger2
public class Knife4jConfig {
    @Bean/*(value = "defaultApi2")*/
    public Docket defaultApi2() {

        Docket docket=new Docket(DocumentationType.OAS_30)
                //ApiInfo
                .apiInfo(adminApiInfo())
                .groupName("w我是groupName")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.lwq"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("我是title")
                .description("我是description")
                .version("我是version-0.0.1")
                .contact(new Contact("hehe", "http://lwq.hehe", "lwq@heh.com"))
                .build();
    }
}
