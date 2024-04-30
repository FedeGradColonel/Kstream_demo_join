package com.luxottica.demo.join.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public abstract class AbstractConsumer<T> {
    private static final Logger log = LoggerFactory.getLogger(AbstractConsumer.class);

    public AbstractConsumer() {
    }

    protected abstract void consumeRecord(ConsumerRecord<String, T> consumerRecord, Acknowledgment acknowledgment);

    protected <D> D consumeValue(T value, Acknowledgment acknowledgment, Function<T, D> mapperFunction, UnaryOperator<D> serviceFunction) {
        Object var6;
        try {
            Optional<D> optionalDomain = Optional.ofNullable(value).map(mapperFunction).map(serviceFunction);
            if (optionalDomain.isPresent()) {
                if (log.isInfoEnabled()) {
                    log.info("Consuming value {}", this.toString(value));
                }

                var6 = optionalDomain.get();
                return (D) var6;
            }

            log.warn("Skipped consuming. See previous log.");
            var6 = null;
            return (D) var6;
        } catch (Exception var10) {
            Exception e = var10;
            log.error(String.format("Error in consuming value %s", Objects.nonNull(value) ? this.toString(value) : "null"), e);
            var6 = null;
        } finally {
            acknowledgment.acknowledge();
        }

        return (D) var6;
    }

    protected String toString(T t) {
        return t.toString();
    }

}
