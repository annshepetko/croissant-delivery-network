package com.ann.delivery.kafka.topic.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaUserTopicConfig {

    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String, Object> kafkaTopicsCredentials = new HashMap<>();
        kafkaTopicsCredentials.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        return new KafkaAdmin(kafkaTopicsCredentials);
    }

    @Bean
    public NewTopic topicOrder(){
        return TopicBuilder
                .name("forgot-password")
                .partitions(10)
                .replicas(1)
                .build();
    }

}
