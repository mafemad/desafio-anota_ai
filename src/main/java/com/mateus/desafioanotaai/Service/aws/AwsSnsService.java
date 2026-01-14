package com.mateus.desafioanotaai.Service.aws;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.Topic;

@Service
public class AwsSnsService {

    private final SnsClient snsClient;
    private final Topic catalogTopic;

    public AwsSnsService(SnsClient snsClient, @Qualifier("catalogEventsTopic") Topic topic) {
        this.snsClient = snsClient;
        this.catalogTopic = topic;
    }

    public void publish(MessageDTO message){
        this.snsClient.publish(builder -> {
            builder.topicArn(catalogTopic.topicArn());
            builder.message(message.message());
        });
    }
}
