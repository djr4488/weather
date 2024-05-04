package org.djr4488.quarkus.controller;

import org.djr4488.quarkus.model.geocode.OpenWeatherGeocodeResponse;
import org.djr4488.quarkus.model.onecall.OpenWeatherOneCallResponse;
import org.djr4488.quarkus.model.rss.RssResponse;
import org.djr4488.quarkus.model.store.Tile;
import org.djr4488.quarkus.model.store.Track;
import org.djr4488.quarkus.model.store.WeatherData;
import org.djr4488.quarkus.model.store.WeatherLocation;
import org.djr4488.quarkus.model.store.WeatherSearch;
import org.djr4488.quarkus.services.OpenWeatherGeocodeClient;
import org.djr4488.quarkus.services.OpenWeatherOneCallClient;
import org.djr4488.quarkus.services.UsccbRssFeed;
import org.djr4488.quarkus.services.database.DatabaseService;
import org.djr4488.quarkus.utils.WeatherUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    public OpenWeatherOneCallResponse getWeatherFullByLatLon(String lat, String lon, String ipAddress, String altitude, String heading,
                                                             String speed) {
        final LocalDateTime txTime = LocalDateTime.now();
        log.info("getWeatherFullByLatLon() lat:{}, lon:{}", lat, lon);
        WeatherLocation weatherLocation = databaseService.findWeatherLocationByLatLon(lat, lon);
        OpenWeatherGeocodeResponse geocodeResponse;
        if (weatherLocation == null) {
            geocodeResponse = geocodeClient.getGeocode(lat, lon, "1", apiKey).get(0);
            WeatherSearch weatherSearch = new WeatherSearch();
            weatherSearch.setLocation(geocodeResponse.getZip());
            weatherSearch.setLocalDateTime(txTime);
            databaseService.save(weatherSearch);

            weatherLocation = new WeatherLocation();
            weatherLocation.setLocation(geocodeResponse.toString());
            weatherLocation.setLocalDateTime(txTime);
            weatherLocation.setWeatherSearch(weatherSearch);
            weatherLocation.setLat(geocodeResponse.getLat().toString());
            weatherLocation.setLon(geocodeResponse.getLon().toString());
            weatherLocation.setLocationName(geocodeResponse.getName());
            databaseService.save(weatherLocation);
        }
        Track track = new Track();
        track.setLocalDateTime(LocalDateTime.now());
        track.setIpAddress(ipAddress);
        track.setLat(lat);
        track.setLon(lon);
        track.setAltitude(altitude);
        track.setHeading(heading);
        track.setSpeed(speed);
        track.setWeatherLocation(weatherLocation);
        databaseService.save(track);
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
        return response;
    }

    public OpenWeatherOneCallResponse loadMostRecentSearchedWeatherDataForLocation(String location) {
        OpenWeatherOneCallResponse response;
        WeatherData weatherData = databaseService.findMostRecentWeatherDataForLocation(location);
        if (weatherData != null) {
            response = JsonbBuilder.create().fromJson(weatherData.getWeather(), OpenWeatherOneCallResponse.class);
        } else {
            response = null;
        }
        return response;
    }

    public List<Track> getTracksForToday() {
        return databaseService.findTracksSince(LocalDateTime.now().toLocalDate().atStartOfDay());
    }

    public byte[] getTileImageData(final String s, final Integer x, final Integer y, final Integer z) {
        final Tile tile = databaseService.findTileBySXYZ(s, x, y, z);
        final byte[] imageData;
        if (tile == null) {
            log.info("getTileImageData() tile s:{}, x:{}, y:{}, z:{} not found in database, retrieving from open street maps",
                    s, x, y, z);
            HttpClient client = HttpClient.newBuilder()
                                          .version(HttpClient.Version.HTTP_2)
                                          .build();
            HttpRequest request = HttpRequest.newBuilder()
                                             .GET()
                                             .uri(URI.create("http://" + s + ".tile.openstreetmap.org/" + z + "/" + x + "/" + y + ".png"))
                                             .setHeader("User-Agent", "Java 17 Quarkus Weather App") // add request header
                                             .header("Accept-Language", "en-US")
                                             .header("Content-Type", "application/x-www-form-urlencoded")
                                             .build();
            try {
                imageData = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
                Tile toSave = new Tile();
                toSave.setS(s);
                toSave.setX(x);
                toSave.setY(y);
                toSave.setZ(z);
                toSave.setImageData(imageData);
                toSave.setLocalDateTime(LocalDateTime.now());
                databaseService.save(toSave);
            } catch (Exception ex) {
                return null;
            }
        } else {
            log.info("getTileImageData() tile s:{}, x:{}, y:{}, z:{}  found in database, returning tile:{}",
                    tile.getId());
            imageData = tile.getImageData();
        }
        return imageData;
    }

    public Set<String> loadDistinctWeatherLocations() {
        Set<String> weatherLocations = databaseService.findAllDistinctWeatherLocations();
        return weatherLocations;
    }
}
