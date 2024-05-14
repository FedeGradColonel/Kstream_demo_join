package com.luxottica.demo.join.kstream;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import org.apache.kafka.common.serialization.Deserializer;
import org.hl7.fhir.r4.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class FhirDeserializer<R extends Resource> implements Deserializer<R> {
    private static final Logger log = LoggerFactory.getLogger(FhirDeserializer.class);
    private static final IParser fhirParser = FhirContext.forR4().newJsonParser();
    private final Class<R> fhirResourceClass;

    public FhirDeserializer(Class<R> fhirResourceClass) {
        this.fhirResourceClass = fhirResourceClass;
    }

    public R deserialize(String s, byte[] bytes) {
        String deserialized = new String(bytes, StandardCharsets.UTF_8);

        try {
            return fhirParser.parseResource(this.fhirResourceClass, deserialized);
        } catch (DataFormatException var5) {
            DataFormatException e = var5;
            log.error("Error deserializing message: {}, error: {}", deserialized, e.getMessage());
            return null;
        }
    }
}
