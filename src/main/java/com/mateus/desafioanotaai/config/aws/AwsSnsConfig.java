package com.mateus.desafioanotaai.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.Topic;

@Configuration
public class AwsSnsConfig {

    @Value("${aws.region}")
    private String region;
    @Value("${aws.access-key-id}")
    private String accessKeyId;
    @Value("${aws.secret-access-key}")
    private String secretAccessKey;
    @Value("${aws.sns.topic-arn}")
    private String catalogTopicArn;

    @Bean
    public SnsClient snsClient(){
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        return SnsClient.builder()
                .credentialsProvider(() -> credentials)
                .region(software.amazon.awssdk.regions.Region.of(region))
                .build();
    }

    @Bean(name = "catalogEventsTopic")
    public Topic snsCatalogTopic(){
        return Topic.builder()
                .topicArn(catalogTopicArn)
                .build();
    }

}
