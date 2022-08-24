package org.djr4488.quarkus.controller;

import org.djr4488.quarkus.model.geocode.OpenWeatherGeocodeResponse;
import org.djr4488.quarkus.model.onecall.OpenWeatherOneCallResponse;
import org.djr4488.quarkus.model.openweather.OpenWeatherResponse;
import org.djr4488.quarkus.services.OpenWeatherGeocodeClient;
import org.djr4488.quarkus.services.OpenWeatherMapClient;
import org.djr4488.quarkus.services.OpenWeatherOneCallClient;
import org.djr4488.quarkus.utils.WeatherUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;

@ApplicationScoped
public class WeatherController {
    @ConfigProperty(name = "WeatherController.apiKey")
     String apiKey;
    @Inject
     Logger log;
    @Inject
    @RestClient
     OpenWeatherMapClient weatherClient;
    @Inject
    @RestClient
    OpenWeatherGeocodeClient geocodeClient;
    @Inject
    @RestClient
    OpenWeatherOneCallClient oneCallClient;

    public OpenWeatherResponse getWeather(String zip) {
        System.out.println("getWeather() apiKey:" + apiKey);
        log.info("getWeather() zip:{}", zip);
        OpenWeatherResponse response = weatherClient.getWeatherByZip(zip + ",us", apiKey, "imperial");
        log.info("getWeather() response:{}", response);
        System.out.println("getWeather() response:" + response.toString());
        return response;
    }

    public OpenWeatherOneCallResponse getFullWeather(String zipCode) {
        log.info("getFullWeather() entered zipCode:{},apiKey:{}", zipCode, apiKey);
        OpenWeatherGeocodeResponse geocodeResponse = geocodeClient.getGeocode(zipCode, apiKey);
        OpenWeatherOneCallResponse response = oneCallClient.getOneCallResponse(geocodeResponse.getLat().toString(), geocodeResponse.getLon().toString(), apiKey, "imperial");
        response.setName(geocodeResponse.getName());
        final LocalDateTime sunrise = response.getCurrent().getSunrise();
        final LocalDateTime sunset = response.getCurrent().getSunset();
        response.getCurrent().setIsDaytime(WeatherUtils.isDaytime(sunrise, sunset));
        log.info("getFullWeather() response:{}", response);
        System.out.println("getWeather() response:" + response.toString());
        return response;
    }
}
