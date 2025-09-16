package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean //@Bean 是标注在方法上的注解，不是类注解。它把该方法的返回值注册到 Spring 容器，成为一个 Bean。通常放在 @Configuration 类里
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){ //factory不需要创建，由引入坐标带入
        log.info("开始配置redisTemplate");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置key的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置value的序列化器
        // redisTemplate.setValueSerializer(new StringRedisSerializer());
        // 使配置生效
        redisTemplate.afterPropertiesSet();
        log.info("redisTemplate配置完成");
        return redisTemplate;

    }
}
