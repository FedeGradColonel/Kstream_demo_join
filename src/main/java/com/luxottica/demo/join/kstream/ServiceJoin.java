package com.luxottica.demo.join.kstream;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hl7.fhir.r4.model.Appointment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@Slf4j
public class ServiceJoin {

    @KafkaListener(topics = "appointment", containerFactory = "practitionerAndRoleListener")
    public void consumeRecord(ConsumerRecord<String, Appointment> consumerRecord,
                              Acknowledgment acknowledgment) {
        Appointment appointment = consumerRecord.value();

        System.out.println(appointment);

        try {
//            PractitionerDomain practitionerDomain =
//                    practitionerAndRoleMapper.fromPractitionerAndRoleToPractitionerDomain(appointment);
//            PractitionerDomain savedPractitioner = practitionerService.save(practitionerDomain);
//            PractitionerRoleDomain practitionerRoleDomain = practitionerAndRoleMapper
//                    .fromPractitionerAndRoleToPractitionerRoleDomain(appointment, savedPractitioner);
//            practitionerRoleService.save(practitionerRoleDomain);
        } catch (Exception e) {

        } finally {
            acknowledgment.acknowledge();
        }
    }

}
