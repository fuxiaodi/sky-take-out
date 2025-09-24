package com.sky;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication //the main class responsible for launching the application.
@EnableTransactionManagement //开启注解方式的事务管理，注解告诉 Spring 开启对 @Transactional 注解的解析和代理。
@Slf4j
@EnableCaching //开启缓存注解功能
@EnableScheduling //开启任务调度
public class SkyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkyApplication.class, args); //静态方法
        log.info("server started");
    }
}
