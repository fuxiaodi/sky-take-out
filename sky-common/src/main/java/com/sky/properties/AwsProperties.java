package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.aws")
@Data
public class AwsProperties {

    private String region;
    private Credentials credentials;
    private S3 s3;

    // getters and setters

    @Data
    public static class S3 {
        private String bucket;
        // getters and setters
    }


    @Data
    public static class Credentials {
        private String accessKey;
        private String secretKey;
        // getters and setters
    }

}
