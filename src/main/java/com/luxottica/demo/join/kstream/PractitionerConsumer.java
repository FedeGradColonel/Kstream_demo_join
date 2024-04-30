package com.luxottica.demo.join.kstream;

import com.luxottica.demo.join.config.AbstractConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@Slf4j
public class PractitionerConsumer extends AbstractConsumer<Practitioner> {

    @Override
    @KafkaListener(topics = "practitioner", containerFactory = "")
    public void consumeRecord(ConsumerRecord<String, Practitioner> consumerRecord,
                              Acknowledgment acknowledgment) {
        Practitioner practitioner = consumerRecord.value();

        System.out.println(practitioner);

        try {

        } catch (Exception e) {

        } finally {
            acknowledgment.acknowledge();
        }
    }

}
