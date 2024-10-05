package com.ann.delivery.kafka.provider.config;


import com.ann.delivery.kafka.provider.notification.dto.UserForgotPasswordNotification;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaUserProducerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfig(){

        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);

        return  configs;
    }

    @Bean
    public KafkaTemplate<String, UserForgotPasswordNotification> emailKafkaTemplate(){
        return new KafkaTemplate(producerUserFactory());
    }

    @Bean
    public ProducerFactory<String, UserForgotPasswordNotification> producerUserFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }



}
