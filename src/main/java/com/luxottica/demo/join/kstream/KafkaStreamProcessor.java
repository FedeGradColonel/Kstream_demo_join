package com.luxottica.demo.join.kstream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.hl7.fhir.r4.model.Appointment;
import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaStreamProcessor {

    private final String APPOINTMENT_TOPIC = "appointment";
    private final String PRACTITIONER_TOPIC = "practitioner";
    private final String OUTPUT_JOIN_TOPIC = "practitioner";

    @Value("${spring.kafka.streams.bootstrap-servers}")
    private String boostrapServer;

    @Value("${spring.kafka.streams.application-id}")
    private String applicationId;

    @Bean
    public KafkaStreams KafkaProcessor() {
        Properties properties = getProperties();

        StreamsBuilder builder = new StreamsBuilder();

        // Definizione del primo stream 'appointment'
        KTable<String, Appointment> appointmentStream = builder.table(APPOINTMENT_TOPIC,
                Consumed.with(Serdes.String(), Serdes.serdeFrom(new FhirSerializer<>(), new FhirDeserializer<>(Appointment.class))));

        // Definizione del secondo stream 'practitioner' come KTable
        KTable<String, Practitioner> practitionerTable = builder.table(PRACTITIONER_TOPIC,
                Consumed.with(Serdes.String(), Serdes.serdeFrom(new FhirSerializer<>(), new FhirDeserializer<>(Practitioner.class))));

        // Join tra lo stream 'appointment' e la 'practitioner' KTable
        appointmentStream
                .join(practitionerTable, new MyValueJoiner())
                .toStream()
                .to(OUTPUT_JOIN_TOPIC);

        KafkaStreams streams = new KafkaStreams(builder.build(), properties);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        return streams;
    }

    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServer);
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        return properties;
    }

}
