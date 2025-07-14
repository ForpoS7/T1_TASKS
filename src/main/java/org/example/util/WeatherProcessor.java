package org.example.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.entity.City;
import org.example.entity.Weather;
import org.example.factory.WeatherFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class WeatherProcessor {
    private final WeatherFactory weatherFactory;

    public List<Weather> prepareWeatherData(List<City> cities) {
        List<Weather> weatherList = new ArrayList<>();
        for (City city : cities) {
            LocalDate currentDate = LocalDate.now();
            for (int i = 0; i < 7; i++) {
                Weather weather = weatherFactory.createWeather(city, currentDate);
                weatherList.add(weather);
                currentDate = currentDate.plusDays(1);
            }
        }
        return weatherList;
    }


}
