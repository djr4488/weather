package org.djr4488.quarkus.utils;

import java.time.LocalDateTime;

public class WeatherUtils {
    public static Boolean isDaytime(LocalDateTime sunrise, LocalDateTime sunset) {
        final LocalDateTime now = LocalDateTime.now();
        return (now.isAfter(sunrise) || now.isEqual(sunrise)) && (now.isBefore(sunset));
    }
}
