package com.stonedt.intelligence.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 文轩
 * @description: swagger配置类 <br>
 */
@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "token";

    /**
     * 配置springdoc
     */
    @Bean
    public OpenAPI springDocConfig() {
        // 配置swagger文档信息,请求头中添加token,必填项
        //配置扫描包
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("开源舆情接口文档")
                        .description("开源舆情接口文档")
                        .version("1.0.0"))

                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme("bearer")
                                        .description("鉴权token")
                                        .bearerFormat("JWT")
                        ));
    }



}
