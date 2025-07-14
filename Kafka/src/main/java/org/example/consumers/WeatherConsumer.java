package org.example.consumers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.messages.WeatherMessage;
import org.example.util.MessageHandler;
import org.example.util.MessageMapper;

import java.time.Duration;
import java.util.Collections;

@RequiredArgsConstructor
@Getter
@Setter
public class WeatherConsumer {
    private final KafkaConsumer<String, String> consumer;
    private final String topic;

    public void consume(MessageHandler messageHandler) {
        consumer.subscribe(Collections.singletonList(topic));

        System.out.println("Consumer started.");

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record : records) {
                    WeatherMessage weatherMessage = MessageMapper.fromJson(record.value(), WeatherMessage.class);
                    messageHandler.handle(weatherMessage);
                }
            }
        } finally {
            consumer.close();
        }
    }
}
