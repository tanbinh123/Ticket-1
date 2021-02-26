package com.woniuxy.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class ConstantProperties {
    private String endPoint;
    private String keyId;
    private String keySecret;
    private String bucketName;
    private String fileHost;
}