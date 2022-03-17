package com.chengyan.webapp.ServiceController;

import com.chengyan.webapp.ConfigController.AwsS3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.exception.SdkServiceException;
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
        try {
            s3 = S3Client.builder().region(awsS3Config.getRegion()).build();
        } catch (SdkServiceException e) {
            e.printStackTrace();
        }
    }

    public void uploadFile(String key, byte[] bytes, Map<String, String> metadata) {
        try {
            s3.putObject(
                    PutObjectRequest.builder()
                            .metadata(metadata)
                            .contentType(metadata.get("contentType"))
                            .bucket(awsS3Config.getBucketName())
                            .key(key)
                            .build(),
                    RequestBody.fromBytes(bytes)
            );
        } catch (SdkServiceException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String key) {
        try {
            s3.deleteObject(DeleteObjectRequest.builder()
                    .bucket(awsS3Config.getBucketName())
                    .key(key)
                    .build());
        } catch (SdkServiceException e) {
            e.printStackTrace();
        }
    }

    public String getProfilePicPath(String userId) {
        return String.join("/", userId, "profile_pic");
    }

    public String getProfilePicUrl(String profilePicPath) {
        return String.join("/", awsS3Config.getBucketName(), profilePicPath);
    }
}
