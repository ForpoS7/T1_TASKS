package org.example.messages;

import lombok.*;
import org.example.entity.Weather;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class WeatherMessage {
    private List<Weather> weathers;
}
