package com.luxottica.demo.join.kstream;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import org.apache.kafka.common.serialization.Serializer;
import org.hl7.fhir.r4.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class FhirSerializer<R extends Resource> implements Serializer<R> {

    private static final Logger log = LoggerFactory.getLogger(FhirSerializer.class);
    private static final IParser fhirParser = FhirContext.forR4().newJsonParser();

    public FhirSerializer() {
    }

    public byte[] serialize(String s, R fhirResource) {
        try {
            return this.serializeToJson(fhirResource).getBytes(StandardCharsets.UTF_8);
        } catch (NullPointerException | DataFormatException var4) {
            RuntimeException e = var4;
            log.error("Error serializing message for resource {}, exception {}", fhirResource, e.getMessage());
            return new byte[0];
        }
    }

    public String serializeToJson(R fhirResource) {
        return fhirParser.encodeResourceToString(fhirResource);
    }

}
