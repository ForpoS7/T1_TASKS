package org.example.factory;

import org.example.entity.City;
import org.example.entity.Weather;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WeatherFactory {
    private static final String[] CONDITIONS = {"Солнечно", "Облачно", "Дождь", "Снег", "Туман"};

    private int generateTemperature() {
        return ThreadLocalRandom.current().nextInt(-30, 31);
    }

    private String generateCondition() {
        Random random = new Random();
        return CONDITIONS[random.nextInt(CONDITIONS.length)];
    }

    public Weather createWeather(City city, LocalDate date) {
        int temperature = generateTemperature();
        String condition = generateCondition();
        return new Weather(city, temperature, condition, date);
    }
}
