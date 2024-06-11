package com.luxottica.demo.join.kstream;

import org.apache.kafka.streams.kstream.ValueJoiner;
import org.hl7.fhir.r4.model.Appointment;
import org.hl7.fhir.r4.model.Practitioner;

public class MyValueJoiner implements ValueJoiner<Appointment, Practitioner, String> {


    @Override
    public String apply(Appointment appointment, Practitioner practitioner) {
        if (practitioner == null) {
            return "Appointment: " + appointment + ", Practitioner: <not found>";
        } else {
            return "Appointment: " + appointment + ", Practitioner: " + practitioner;
        }
    }

}
