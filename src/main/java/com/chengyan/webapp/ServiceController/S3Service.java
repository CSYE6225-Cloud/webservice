package com.chengyan.webapp.ServiceController;

import com.chengyan.webapp.ConfigController.AwsS3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class S3Service {

    private S3Client s3 = null;

    @Autowired
    private AwsS3Config awsS3Config;

    @PostConstruct
    private void s3Init() {
        instantiate();
    }

    private void instantiate() {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(
                awsS3Config.getAccessKeyId(),
                awsS3Config.getSecretAccessKey()
        );

        s3 = S3Client.builder()
                .region(awsS3Config.getRegion())
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }

    public void uploadFile(String key, byte[] bytes, Map<String, String> metadata) {
        s3.putObject(
                PutObjectRequest.builder().metadata(metadata).bucket(awsS3Config.getBucketName()).key(key).build(),
                RequestBody.fromBytes(bytes)
        );
    }

    public void deleteFile(String key) {
        try {
            s3.deleteObject(DeleteObjectRequest.builder()
                    .bucket(awsS3Config.getBucketName())
                    .key(key)
                    .build());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getProfilePic(String userId, String suffix) {
        return String.join("/", userId, "profile_pic."+suffix);
    }
}
