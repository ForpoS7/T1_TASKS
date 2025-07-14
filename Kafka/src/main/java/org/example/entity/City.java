package org.example.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class City {
    private final String name;

    @JsonCreator
    public City(@JsonProperty("name") String name) {
        this.name = name;
    }
}
