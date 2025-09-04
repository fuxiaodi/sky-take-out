package com.sky.config;

import com.sky.properties.AwsProperties;
import com.sky.utils.AwsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于创建aws对象
 */
@Configuration
@Slf4j
public class AwsConfiguration {

    @Bean  //启动时创建对象
    @ConditionalOnMissingBean  //没必要创建多个，没有时才创建
    public AwsUtil awsUtil(AwsProperties awsProperties){
        log.info("开始创建aws文件上传工具类对象：{}", awsProperties);
        return new AwsUtil(awsProperties.getRegion(),
                awsProperties.getCredentials(),
                awsProperties.getS3());
    }

}
