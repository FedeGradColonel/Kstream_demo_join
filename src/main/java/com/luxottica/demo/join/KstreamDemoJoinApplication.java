package com.luxottica.demo.join;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
//@EnableKafkaStreams
public class KstreamDemoJoinApplication {

    public static void main(String[] args) {
        SpringApplication.run(KstreamDemoJoinApplication.class, args);
    }

}
