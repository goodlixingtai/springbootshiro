package com.yonyou.springbootshiro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-05 22:42
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
 /*   @Configuration声明为配置类
    @EnableSwagger2则开启Swagger2文档支持，其余大致为固定写法，只是具体属性有所不同。
    basePackage指明文档包含的包范围（主要配置点）
   */

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                /*.apis(RequestHandlerSelectors.basePackage("com.yonyou.springbootshir."))*/
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
                //RequestHandlerSelectors.any()对所有路径
    }

    private ApiInfo apiInfo(){
    /* title和description即为文档标题和描述
    contact为创建人信息，
    version是版本信息。*/
        return new ApiInfoBuilder()
                .title("SpringBoot shiro Doc")
                .description("This is a restful api document of Spring Boot.")
                .version("1.0")
                .build();
    }

}
