package org.example;

import org.example.config.KafkaConfiguration;
import org.example.consumers.WeatherConsumer;
import org.example.entity.City;
import org.example.factory.WeatherFactory;
import org.example.messages.WeatherMessage;
import org.example.producers.WeatherProducer;
import org.example.util.MessageHandler;
import org.example.util.MessageMapper;
import org.example.util.WeatherHandler;
import org.example.util.WeatherProcessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


public class KafkaMessageTest {
    public static final List<City> CITIES = Arrays.asList(
            new City("Moscow"),
            new City("Saransk"),
            new City("Kazan")
    );

    private static WeatherProducer producer;
    private static WeatherConsumer consumer;
    private WeatherMessage weatherMessage;
    private MessageHandler messageHandler;

    @BeforeEach
    void setUp() {
        KafkaConfiguration kafkaConfiguration = new KafkaConfiguration("kafka.properties");
        producer = new WeatherProducer(kafkaConfiguration.createProducer(), kafkaConfiguration.getTopic());
        consumer = new WeatherConsumer(kafkaConfiguration.createConsumer(), kafkaConfiguration.getTopic());
        messageHandler = new WeatherHandler();
        WeatherFactory weatherFactory = new WeatherFactory();
        WeatherProcessor weatherProcessor = new WeatherProcessor(weatherFactory);
        weatherMessage = new WeatherMessage(weatherProcessor.prepareWeatherData(CITIES));
    }

    @Test
    void testSendMessageAndConsume() {
        String jsonMessage = MessageMapper.toJson(weatherMessage);
        producer.send(jsonMessage);

        Thread consumerThread = new Thread(() -> consumer.consume(messageHandler));
        consumerThread.start();

        try {
            consumerThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void tearDownClass() {
        if (producer.getProducer() != null) {
            producer.getProducer().close();
        }
    }
}
