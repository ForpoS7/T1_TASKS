package org.example.util;

import org.example.messages.WeatherMessage;

@FunctionalInterface
public interface MessageHandler {
    void handle(WeatherMessage weatherMessage);
}
