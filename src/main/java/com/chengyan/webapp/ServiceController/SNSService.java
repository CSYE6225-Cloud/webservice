package com.chengyan.webapp.ServiceController;

import com.chengyan.webapp.ConfigController.AwsS3Config;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class SNSService {
    private SnsClient snsClient;

    @Autowired
    private AwsS3Config awsS3Config;

    @PostConstruct
    public void snsInit() {
        snsClient = SnsClient.builder()
                .region(awsS3Config.getRegion())
                .build();
    }

    public void publishMessageToSns(Map<String, String> snsMessage) {
        Gson gson = new Gson();
        String message = gson.toJson(snsMessage);
        publishMessage(message);
    }

    private void publishMessage(String message) {
        System.out.println(message);
        try {
            PublishRequest publishRequest = PublishRequest.builder()
                    .message(message)
                    .topicArn(awsS3Config.getTopicArn())
                    .build();
            

            PublishResponse result = snsClient.publish(publishRequest);
            System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
