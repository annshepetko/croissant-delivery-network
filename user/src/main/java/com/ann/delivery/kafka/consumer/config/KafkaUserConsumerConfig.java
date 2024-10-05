package com.ann.delivery.kafka.consumer.config;

import com.ann.delivery.kafka.consumer.dto.UserNotification;
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

@Configuration
@EnableKafka
public class KafkaUserConsumerConfig {


    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, UserNotification> consumerUserNotificationFactory(){

        JsonDeserializer<UserNotification> emailDeserializer = new JsonDeserializer<>(UserNotification.class);
        emailDeserializer.setRemoveTypeHeaders(false);
        emailDeserializer.addTrustedPackages("com.ann.delivery.kafka.consumer.dto");
        emailDeserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<>(consumerUserConfigs(), new StringDeserializer(), emailDeserializer);
    }

    @Bean
    public Map<String, Object> consumerUserConfigs(){
        Map<String, Object> configs = new HashMap<>();

        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return configs;
    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UserNotification>> kafkaListenerContainerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, UserNotification> listenerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        listenerFactory.setConsumerFactory(consumerUserNotificationFactory());

        return listenerFactory;
    }


}
