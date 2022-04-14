package com.chengyan.webapp.ServiceController;

import com.chengyan.webapp.ConfigController.AwsS3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class DynamoDbService {

    private DynamoDbClient client;

    final private static int ITEM_EXPIRED_MINUTES = 5;

    @Autowired
    private AwsS3Config awsS3Config;

    @PostConstruct
    public void dynamoDbInit() {
        try {
            this.client = DynamoDbClient.builder()
                    .region(awsS3Config.getRegion())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putItem(String email, String token) {
        Map<String, AttributeValue> itemMap = new HashMap<>();
        itemMap.put("email", AttributeValue.builder().s(email).build());
        itemMap.put("token", AttributeValue.builder().s(token).build());

        int expiredTime = awsS3Config.getDynamodbExpiredTime() == null
                ? ITEM_EXPIRED_MINUTES
                :  Integer.parseInt(awsS3Config.getDynamodbExpiredTime());
        String expire_at = String.valueOf(System.currentTimeMillis() / 1000L) + (60L * expiredTime);
        System.out.println(expire_at);
        itemMap.put("expire_at", AttributeValue.builder().n(expire_at).build());

        PutItemRequest request = PutItemRequest.builder()
                .item(itemMap)
                .tableName(awsS3Config.getDynamodbTableName())
                .build();
        try {
            client.putItem(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifyToken(String email, String token, String curTime) {
        Map<String, AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put("email", AttributeValue.builder().s(email).build());
        GetItemRequest request = GetItemRequest.builder()
                .tableName(awsS3Config.getDynamodbTableName())
                .key(keyToGet)
                .build();
        boolean res = false;
        try {
            Map<String, AttributeValue> itemMap = client.getItem(request).item();
            if (itemMap!=null) {
                if (itemMap.get("expire_at").n().compareTo(String.valueOf(curTime)) <=0
                        && itemMap.get("token").s().equals(token))
                    res = true;
            }
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }

        return res;
    }

    public void getItem(String email) {
        Map<String, AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put("email", AttributeValue.builder().s(email).build());
        GetItemRequest request = GetItemRequest.builder()
                .tableName(awsS3Config.getDynamodbTableName())
                .key(keyToGet)
                .build();
        try {
            Map<String, AttributeValue> itemMap = client.getItem(request).item();
            System.out.println(itemMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateItem() {

    }

    public void deleteItem(String email) {
        Map<String, AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put("email", AttributeValue.builder().s(email).build());
        DeleteItemRequest deleteRequest = DeleteItemRequest.builder()
                .tableName(awsS3Config.getDynamodbTableName())
                .key(keyToGet)
                .build();
        try {
            client.deleteItem(deleteRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
