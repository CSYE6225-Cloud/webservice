package com.chengyan.webapp.ConfigController;

import org.springframework.boot.context.properties.ConfigurationProperties;
import software.amazon.awssdk.regions.Region;

@ConfigurationProperties(prefix = "aws")
public class AwsS3Config {
    private String bucketName;
    private Region region;
    private String dynamodbTableName;
    private String dynamodbEmailTrackerTableName;
    private String topicArn;
    private String dynamodbExpiredTime; // in minutes

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

    public String getDynamodbTableName() {
        return dynamodbTableName;
    }

    public void setDynamodbTableName(String dynamodbTableName) {
        this.dynamodbTableName = dynamodbTableName;
    }

    public String getTopicArn() {
        return topicArn;
    }

    public void setTopicArn(String topicArn) {
        this.topicArn = topicArn;
    }

    public String getDynamodbExpiredTime() {
        return dynamodbExpiredTime;
    }

    public void setDynamodbExpiredTime(String dynamodbExpiredTime) {
        this.dynamodbExpiredTime = dynamodbExpiredTime;
    }

    public String getDynamodbEmailTrackerTableName() {
        return dynamodbEmailTrackerTableName;
    }

    public void setDynamodbEmailTrackerTableName(String dynamodbEmailTrackerTableName) {
        this.dynamodbEmailTrackerTableName = dynamodbEmailTrackerTableName;
    }
}
