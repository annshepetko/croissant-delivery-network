package com.delivery.order.kafka.config;

import com.delivery.order.kafka.notification.EmailNotification;
import com.delivery.order.kafka.notification.UserNotification;
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
public class KafkaTemplateConfig {


    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, EmailNotification> producerEmailFactory() {
        JsonSerializer<EmailNotification> serializer = new JsonSerializer<>();

        return new DefaultKafkaProducerFactory<>(emailProducerConfigs(), new StringSerializer(), serializer);
    }

    @Bean
    public Map<String, Object> emailProducerConfigs(){

        Map<String, Object> configs = new HashMap<>();

        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,  JsonSerializer.class);

        return configs;
    }

    @Bean
    public KafkaTemplate<String, EmailNotification> emailKafkaTemplate(){

        return new KafkaTemplate(producerEmailFactory());
    }

    @Bean
    public ProducerFactory<String, UserNotification> producerUserFactory() {
        return new DefaultKafkaProducerFactory<>(emailProducerConfigs());
    }


    @Bean
    public KafkaTemplate<String, UserNotification> userKafkaTemplate(){
        return new KafkaTemplate(producerUserFactory());
    }
}
