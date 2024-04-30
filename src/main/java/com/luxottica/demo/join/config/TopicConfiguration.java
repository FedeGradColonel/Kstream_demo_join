package com.luxottica.demo.join.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {

    private final String APPOINTMENT_TOPIC = "appointment";
    private final String PRACTITIONER_TOPIC = "practitioner";
    private final String APPOINTMENT_PRACTITONER_TOPIC = "appointment-practitioner-join";

    @Bean
    public NewTopic appointmentTopic() {
        return TopicBuilder.name(APPOINTMENT_TOPIC)
                .partitions(3)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, "604800000")
                .config(TopicConfig.SEGMENT_MS_CONFIG, "604800000")
                .config(TopicConfig.MESSAGE_TIMESTAMP_TYPE_CONFIG, "CreateTime")
                .build();
    }

    @Bean
    public NewTopic practitionerTopic() {
        return TopicBuilder.name(PRACTITIONER_TOPIC)
                .partitions(3)
                .replicas(1)
                .config(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_COMPACT)
                .config(TopicConfig.RETENTION_MS_CONFIG, "-1")
                .config(TopicConfig.SEGMENT_MS_CONFIG, "604800000")
                .config(TopicConfig.MESSAGE_TIMESTAMP_TYPE_CONFIG, "CreateTime")
                .build();
    }

    @Bean
    public NewTopic outputTopic() {
        return TopicBuilder.name(APPOINTMENT_PRACTITONER_TOPIC)
                .partitions(3)
                .replicas(1)
                .config(TopicConfig.SEGMENT_MS_CONFIG, "604800000")
                .config(TopicConfig.MESSAGE_TIMESTAMP_TYPE_CONFIG, "CreateTime")
                .build();
    }

}
