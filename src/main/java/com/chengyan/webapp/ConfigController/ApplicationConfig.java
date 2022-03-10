package com.chengyan.webapp.ConfigController;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AwsS3Config.class)
public class ApplicationConfig {
}
