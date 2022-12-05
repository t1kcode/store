package com.t1k.store.config;

import com.t1k.store.entity.JsonResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(JsonResult.class)
                .ignoredParameterTypes(HttpSession.class)
                .ignoredParameterTypes(HttpServletRequest.class)
                .ignoredParameterTypes(HttpServletResponse.class)
                .pathMapping("/").select()
                .apis(RequestHandlerSelectors.basePackage("com.t1k.store.controller"))// 扫描哪个接口的包
                .paths(PathSelectors.any()).build()
                .apiInfo(new ApiInfoBuilder()
                                 .title("电脑商城系统")
                                 .description("控制层后端接口文档")
                                 .version("1.1")// 版本信息
                                 .contact(new Contact("t1k", "http://www.takdoor.top", "t1kcode@163.com"))// 开发文档的联系人
                                 .license("This Baidu License").licenseUrl("http://www.baidu.com").build());

    }
}
