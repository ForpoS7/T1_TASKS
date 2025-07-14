package org.example.util;

import org.example.entity.Weather;
import org.example.messages.WeatherMessage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class WeatherHandler implements MessageHandler {
    @Override
    public void handle(WeatherMessage weatherMessage) {
        List<Weather> weatherList = weatherMessage.getWeathers();

        Map<LocalDate, List<Weather>> groupedByDate = groupWeatherByDate(weatherList);
        printWeatherByDate(groupedByDate);

        analyzeRainyDays(weatherList);
        analyzeHottestDay(weatherList);
        analyzeLowestAverageTemperature(weatherList);
    }

    private Map<LocalDate, List<Weather>> groupWeatherByDate(List<Weather> weatherList) {
        return weatherList.stream()
                .collect(Collectors.groupingBy(Weather::getDate));
    }

    private void printWeatherByDate(Map<LocalDate, List<Weather>> groupedWeather) {
        groupedWeather.forEach((date, weatherList) -> {
            System.out.println("Date: " + date);
            weatherList.forEach(weather -> {
                System.out.println("  City: " + weather.getCity().getName());
                System.out.println("  Weather: " + weather);
            });
        });
    }

    private void analyzeRainyDays(List<Weather> weatherList) {
        Map<String, Long> rainyDaysByCity = weatherList.stream()
                .filter(weather -> "Дождь".equalsIgnoreCase(weather.getCondition()))
                .collect(Collectors.groupingBy(
                        weather -> weather.getCity().getName(),
                        Collectors.counting()
                ));

        if (rainyDaysByCity.isEmpty()) {
            System.out.println("За эту неделю дождливых дней не было.");
            return;
        }

        String cityWithMostRainyDays = Collections.max(rainyDaysByCity.entrySet(), Map.Entry.comparingByValue()).getKey();
        long maxRainyDays = rainyDaysByCity.get(cityWithMostRainyDays);

        System.out.printf("Самое большое количество дождливых дней (%d) за эту неделю в городе %s%n",
                maxRainyDays, cityWithMostRainyDays);
    }

    private void analyzeHottestDay(List<Weather> weatherList) {
        Optional<Weather> hottestWeather = weatherList.stream()
                .max(Comparator.comparingInt(Weather::getTemperature));

        hottestWeather.ifPresent(weather -> System.out.printf(
                "Самая жаркая погода была %s в городе %s с температурой %d°C%n",
                weather.getDate(), weather.getCity().getName(), weather.getTemperature()));
    }

    private void analyzeLowestAverageTemperature(List<Weather> weatherList) {
        Map<String, Double> averageTemperatureByCity = weatherList.stream()
                .collect(Collectors.groupingBy(
                        weather -> weather.getCity().getName(),
                        Collectors.averagingInt(Weather::getTemperature)
                ));

        String cityWithLowestAverageTemp = Collections.min(averageTemperatureByCity.entrySet(),
                Map.Entry.comparingByValue()).getKey();
        double lowestAverageTemp = averageTemperatureByCity.get(cityWithLowestAverageTemp);

        System.out.printf("Самая низкая средняя температура за неделю (%.1f°C) в городе %s%n",
                lowestAverageTemp, cityWithLowestAverageTemp);
    }
}
