package com.luxottica.demo.join.kstream;

import com.luxottica.demo.join.config.AbstractConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hl7.fhir.r4.model.Appointment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@Slf4j
public class AppointmentConsumer extends AbstractConsumer<Appointment> {

    @Override
    @KafkaListener(topics = "appointment", containerFactory = "practitionerAndRoleListener")
    public void consumeRecord(ConsumerRecord<String, Appointment> consumerRecord,
                              Acknowledgment acknowledgment) {
        Appointment appointment = consumerRecord.value();

        System.out.println(appointment);

        try {

        } catch (Exception e) {

        } finally {
            acknowledgment.acknowledge();
        }
    }

}
