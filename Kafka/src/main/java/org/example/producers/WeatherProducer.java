package org.example.producers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

@RequiredArgsConstructor
@Getter
@Setter
public class WeatherProducer {
    private final KafkaProducer<String, String> producer;
    private final String topic;

    public void send(String msg) {
        try {
            producer.send(
                    new ProducerRecord<>(topic, null, msg),
                    (metadata, exception) -> {
                        if (exception != null) {
                            System.err.println("Error while sending: " + exception.getMessage());
                        } else {
                            System.out.println("Message sent successfully.");
                            System.out.println("Topic: " + metadata.topic());
                            System.out.println("Partition: " + metadata.partition());
                            System.out.println("Offset: " + metadata.offset());
                        }
                    });
        } catch (Exception e) {
            System.err.println("Error before sending: " + e.getMessage());
        }
    }
}
