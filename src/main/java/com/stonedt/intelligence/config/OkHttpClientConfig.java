package com.stonedt.intelligence.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 文轩
 */
@Configuration
public class OkHttpClientConfig {

    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS);

        return builder.build();
    }
}
