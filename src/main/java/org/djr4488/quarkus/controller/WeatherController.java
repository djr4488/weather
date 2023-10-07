package org.djr4488.quarkus.controller;

import org.djr4488.quarkus.model.geocode.OpenWeatherGeocodeResponse;
import org.djr4488.quarkus.model.onecall.OpenWeatherOneCallResponse;
import org.djr4488.quarkus.model.store.WeatherData;
import org.djr4488.quarkus.model.store.WeatherLocation;
import org.djr4488.quarkus.model.store.WeatherSearch;
import org.djr4488.quarkus.services.OpenWeatherGeocodeClient;
import org.djr4488.quarkus.services.OpenWeatherOneCallClient;
import org.djr4488.quarkus.services.database.DatabaseService;
import org.djr4488.quarkus.utils.WeatherUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class WeatherController {
    @ConfigProperty(name = "WeatherController.apiKey")
     String apiKey;
    @Inject
     Logger log;
    @Inject
    @RestClient
    OpenWeatherGeocodeClient geocodeClient;
    @Inject
    @RestClient
    OpenWeatherOneCallClient oneCallClient;
    @Inject
    DatabaseService databaseService;

    public OpenWeatherOneCallResponse getFullWeather(String zipCode) {
        final LocalDateTime txTime = LocalDateTime.now();
        log.info("getFullWeather() entered zipCode:{},apiKey:{}", zipCode, apiKey);

        WeatherLocation weatherLocation = databaseService.findWeatherLocationByZipCode(zipCode);
        OpenWeatherGeocodeResponse geocodeResponse;
        if (weatherLocation == null) {
            WeatherSearch weatherSearch = new WeatherSearch();
            weatherSearch.setLocation(zipCode);
            weatherSearch.setLocalDateTime(txTime);
            databaseService.save(weatherSearch);

            geocodeResponse = geocodeClient.getGeocode(zipCode, apiKey);
            weatherLocation = new WeatherLocation();
            weatherLocation.setLocation(geocodeResponse.toString());
            weatherLocation.setLocalDateTime(txTime);
            weatherLocation.setWeatherSearch(weatherSearch);
            weatherLocation.setLat(geocodeResponse.getLat().toString());
            weatherLocation.setLon(geocodeResponse.getLon().toString());
            weatherLocation.setLocationName(geocodeResponse.getName());
            databaseService.save(weatherLocation);
        }

        log.info("getFullWeather() weatherLocation:{}", weatherLocation);
        OpenWeatherOneCallResponse response = oneCallClient.getOneCallResponse(weatherLocation.getLat(), weatherLocation.getLon(), apiKey, "imperial");
        response.setPlace(weatherLocation.getLocationName());
        final LocalDateTime sunrise = response.getCurrent().getSunrise();
        final LocalDateTime sunset = response.getCurrent().getSunset();
        response.getCurrent().setIsDaytime(WeatherUtils.isDaytime(sunrise, sunset));
        WeatherData weatherData = new WeatherData();
        weatherData.setWeatherSearch(weatherLocation.getWeatherSearch());
        weatherData.setWeather(response.toString());
        weatherData.setLocalDateTime(txTime);
        databaseService.save(weatherData);
        log.trace("getFullWeather() response:{}", response);
        return response;
    }
}
