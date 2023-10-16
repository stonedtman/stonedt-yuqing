package com.stonedt.intelligence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 	启动类
 */
@SpringBootApplication
@EnableScheduling
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 600)
public class StonedtPortalApplication{

    public static void main(String[] args) {
        SpringApplication.run(StonedtPortalApplication.class, args);



    }
}