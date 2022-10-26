package com.rental.propertysa.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("dev")
public class TopicConfiguration {
    @Value("${kafka.reservationTopic}")
    private String topic;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topic)
                .partitions(10)
                .replicas(1)
                .build();
    }
}
