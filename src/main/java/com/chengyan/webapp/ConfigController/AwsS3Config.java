package com.chengyan.webapp.ConfigController;

import org.springframework.boot.context.properties.ConfigurationProperties;
import software.amazon.awssdk.regions.Region;

@ConfigurationProperties(prefix = "aws")
public class AwsS3Config {
    private String accessKeyId;
    private String secretAccessKey;
    private String bucketName;
    private Region region;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }
}
