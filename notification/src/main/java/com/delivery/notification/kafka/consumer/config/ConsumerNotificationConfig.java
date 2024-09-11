package com.delivery.notification.kafka.consumer.config;

import com.delivery.notification.dto.mail.OrderEmailNotification;
import com.delivery.notification.kafka.consumer.dto.UserResetPasswordNotification;
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
    public ConsumerFactory<String, OrderEmailNotification> consumerEmailNotificationFactory(){

        JsonDeserializer<OrderEmailNotification> emailDeserializer = new JsonDeserializer<>(OrderEmailNotification.class);
        emailDeserializer.setRemoveTypeHeaders(false);
        emailDeserializer.addTrustedPackages("*");
        emailDeserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<>(consumerEmailConfigs(), new StringDeserializer(), emailDeserializer);
    }

    @Bean
    public Map<String, Object> consumerEmailConfigs(){
        Map<String, Object> configs = new HashMap<>();

        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return configs;
    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderEmailNotification>> kafkaListenerUserResetPasswordContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, OrderEmailNotification> listenerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        listenerFactory.setConsumerFactory(consumerEmailNotificationFactory());

        return listenerFactory;
    }



//    @Bean
//    public ConsumerFactory<String, UserResetPasswordNotification> consumerUserResetPasswordNotificationFactory(){
//
//        JsonDeserializer<UserResetPasswordNotification> userEmailDeserializer = new JsonDeserializer<>(UserResetPasswordNotification.class);
//        userEmailDeserializer.setRemoveTypeHeaders(false);
//        userEmailDeserializer.addTrustedPackages("com.delivery.notification.kafka.consumer.dto");
//        userEmailDeserializer.setUseTypeMapperForKey(true);
//
//        return new DefaultKafkaConsumerFactory<>(consumerUserResetPasswordEmailConfigs(), new StringDeserializer(), userEmailDeserializer);
//    }
//
//    @Bean
//    public Map<String, Object> consumerUserResetPasswordEmailConfigs(){
//        Map<String, Object> configs = new HashMap<>();
//
//        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        configs.put(ConsumerConfig.GROUP_ID_CONFIG,"user-forgot-id");
//
//        return configs;
//    }
//
//
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UserResetPasswordNotification>> kafkaListenerContainerFactory(){
//        ConcurrentKafkaListenerContainerFactory<String, UserResetPasswordNotification> listenerFactory = new ConcurrentKafkaListenerContainerFactory<>();
//        listenerFactory.setConsumerFactory(consumerUserResetPasswordNotificationFactory());
//
//        return listenerFactory;
//    }

}
