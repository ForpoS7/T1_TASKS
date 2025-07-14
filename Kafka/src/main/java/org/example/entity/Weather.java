package org.example.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Weather {
    private final City city;
    private final Integer temperature;
    private final String condition;
    private final LocalDate date;

    @JsonCreator
    public Weather(
            @JsonProperty("city") City city,
            @JsonProperty("temperature") Integer temperature,
            @JsonProperty("condition") String condition,
            @JsonProperty("date") LocalDate date) {
        this.city = city;
        this.temperature = temperature;
        this.condition = condition;
        this.date = date;
    }
}
