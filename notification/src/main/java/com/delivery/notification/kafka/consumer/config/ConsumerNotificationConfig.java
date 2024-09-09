package com.delivery.notification.kafka.consumer.config;

import com.delivery.notification.kafka.consumer.dto.EmailNotification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ConsumerNotificationConfig {

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, EmailNotification> consumerEmailNotificationFactory(){

        JsonDeserializer<EmailNotification> emailDeserializer = new JsonDeserializer<>(EmailNotification.class);
        emailDeserializer.setRemoveTypeHeaders(false);
        emailDeserializer.addTrustedPackages("com.delivery.notification.kafka.consumer.dto");
        emailDeserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<>(consumerEmailConfigs(), new StringDeserializer(), emailDeserializer);
    }

    @Bean
    public Map<String, Object> consumerEmailConfigs(){
        Map<String, Object> configs = new HashMap<>();

        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return configs;
    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, EmailNotification>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, EmailNotification> listenerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        listenerFactory.setConsumerFactory(consumerEmailNotificationFactory());

        return listenerFactory;
    }

}
