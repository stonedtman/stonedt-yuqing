package com.stonedt.intelligence.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 文轩
 * @description: swagger配置类 <br>
 */
@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "tokenAuth";

    /**
     * 配置springdoc
     */
    @Bean
    public OpenAPI springDocConfig() {
        // 配置swagger文档信息
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
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("token")
                                        .bearerFormat("JWT")));
    }



}
