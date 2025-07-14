package org.example.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.InputStream;
import java.util.Properties;

public class KafkaConfiguration {
    private final Properties properties;

    public KafkaConfiguration(String resourcePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new RuntimeException("Configuration file not found: " + resourcePath);
            }
            properties = new Properties();
            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Configuration loading error", e);
        }
    }

    public KafkaProducer<String, String> createProducer() {
        return new KafkaProducer<>(properties);
    }

    public KafkaConsumer<String, String> createConsumer() {
        return new KafkaConsumer<>(properties);
    }

    public String getTopic() {
        String topic = properties.getProperty("kafka.topic");
        if (topic == null || topic.isEmpty()) {
            throw new IllegalStateException("Topic is not configured in the properties file.");
        }
        return topic;
    }
}
