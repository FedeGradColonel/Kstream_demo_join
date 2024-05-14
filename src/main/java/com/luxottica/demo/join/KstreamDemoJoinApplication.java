package com.luxottica.demo.join;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.Properties;

@SpringBootApplication
@EnableKafka
//@EnableKafkaStreams
public class KstreamDemoJoinApplication {

    public static void main(String[] args) {
        SpringApplication.run(KstreamDemoJoinApplication.class, args);

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-stream-join-demo");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsBuilder builder = new StreamsBuilder();

        // Definizione del primo stream 'appointment'
        KStream<String, String> appointmentStream = builder.stream("appointment");

        // Definizione del secondo stream 'practitioner' come KTable
        KTable<String, String> practitionerTable = builder.table("practitioner");

        // Join tra lo stream 'appointment' e la 'practitioner' KTable
        appointmentStream
                .join(practitionerTable,
                        (appointmentValue, practitionerValue) -> {
                            // Qui gestisci il caso in cui non ci sia una corrispondenza in practitioner
                            if (practitionerValue == null) {
                                return "Appointment: " + appointmentValue + ", Practitioner: <not found>";
                            } else {
                                return "Appointment: " + appointmentValue + ", Practitioner: " + practitionerValue;
                            }
                        })
                .to("appointment-practitioner-join");

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

}
