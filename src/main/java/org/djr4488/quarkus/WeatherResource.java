package org.djr4488.quarkus;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.djr4488.quarkus.controller.WeatherController;
import org.djr4488.quarkus.initiator.GlobalScheduler;
import org.djr4488.quarkus.model.AudioResponse;
import org.djr4488.quarkus.model.globe.Feature;
import org.djr4488.quarkus.model.globe.Geometry;
import org.djr4488.quarkus.model.globe.GlobeResponse;
import org.djr4488.quarkus.model.globe.Properties;
import org.djr4488.quarkus.model.onecall.OpenWeatherOneCallResponse;
import org.djr4488.quarkus.model.store.WeatherLocation;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Path("api/2.0")
public class WeatherResource {

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance weather(OpenWeatherOneCallResponse weather);
        public static native TemplateInstance globalWeatherCurrent(OpenWeatherOneCallResponse globalWeatherCurrent);
        public static native TemplateInstance audio(AudioResponse audio);
    }

    @Inject
    WeatherController weatherController;
    @Inject
    Logger log;

    @GET
    @Path("weather/full/{zipCode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TemplateInstance weatherFull(@PathParam("zipCode") String zipCode) {
        OpenWeatherOneCallResponse response = weatherController.getFullWeather(zipCode);
        log.info("weatherFull() completed with response.name:{}", response.getPlace());
        return Templates.weather(response);
    }

    @GET
    @Path("weather/full/{lat}/{lon}")
    @Produces(MediaType.APPLICATION_JSON)
    public OpenWeatherOneCallResponse weatherFull(@PathParam("lat") String lat, @PathParam("lon") String lon) {
        OpenWeatherOneCallResponse response = weatherController.getWeatherFullByLatLon(lat, lon);
        log.info("weatherFull() lat:{}, lon:{}, response:{}", lat, lon, response);
        return response;
    }

    @GET
    @Path("audio/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TemplateInstance audio(@PathParam("id") int id) {
        AudioResponse response = new AudioResponse();
        switch (id) {
            case 1:
                response.setAudioSource("/audio/05 Local Forecast (Nighttime).mp3");
                break;
            case 2:
                response.setAudioSource("/audio/07 Regional Forecast (Nighttime).mp3");
                break;
            case 3:
                response.setAudioSource("/audio/09_Global_Forecast_Nighttime.mp3");
                break;
            case 4:
                response.setAudioSource("/audio/11_Global_Forecast_Nighttime_Layer_Only.mp3");
                break;
            default:
                response.setAudioSource("/audio/11_Global_Forecast_Nighttime_Layer_Only.mp3");
                break;
        }
        return Templates.audio(response);
    }

    @GET
    @Path("weather")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TemplateInstance weather(@QueryParam("zipCode") String zipCode) {
        OpenWeatherOneCallResponse response = weatherController.getFullWeather(zipCode);
        log.info("weather() completed with response.place:{}", response.getPlace());
        return Templates.weather(response);
    }

    @GET
    @Path("globeIcon")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GlobeResponse globeWeather(@QueryParam("zipCode") String zipCode) {
        log.info("globeWeather() entered zipCode:{}", zipCode);
        try {
            Thread.sleep(500);
            OpenWeatherOneCallResponse response = weatherController.loadMostRecentSearchedWeatherDataForLocation(zipCode);
            log.info("globeWeather() response from database:{}", response);
            List<BigDecimal> coordinates = new ArrayList<>();
            coordinates.add(response.getLon());
            coordinates.add(response.getLat());
            Properties properties = new Properties(response.getPlace(), response.getLat(), response.getLon(), response.getCurrent().getTemp().toString(), Templates.globalWeatherCurrent(response).render());
            Geometry geometry = new Geometry("point", coordinates);
            Feature feature = new Feature(properties, geometry);
            List<Feature> features = new ArrayList<>();
            features.add(feature);
            return new GlobeResponse(response.getPlace(), response.getCurrent().getTemp().toString(), response.getLat(), response.getLon(), features);
        } catch (Exception ex) {
            log.error("exception:", ex);
            return null;
        }
    }

    @GET
    @Path("globeHour")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GlobeResponse globeHourWeather() {
        log.info("globeHourWeather()");
        try {
            Thread.sleep(500);
            OpenWeatherOneCallResponse response = null;
            OpenWeatherOneCallResponse olatheResponse = null;
            List<Feature> features = new ArrayList<>();
            Set<String> locations = weatherController.loadDistinctWeatherLocations();
            for (String location : locations) {
                response = weatherController.loadMostRecentSearchedWeatherDataForLocation(location);
                if (response != null) {
                    log.info("globeWeather() response from database:{}", response);
                    List<BigDecimal> coordinates = new ArrayList<>();
                    coordinates.add(response.getLon());
                    coordinates.add(response.getLat());
                    Properties properties = new Properties(response.getPlace(), response.getLat(), response.getLon(), response.getCurrent().getTemp().toString(), Templates.globalWeatherCurrent(response).render());
                    Geometry geometry = new Geometry("point", coordinates);
                    Feature feature = new Feature(properties, geometry);
                    features.add(feature);
                    if (response.getPlace().equalsIgnoreCase("olathe")) {
                        olatheResponse = response;
                    }
                }
            }
            return new GlobeResponse(olatheResponse.getPlace(), olatheResponse.getCurrent().getTemp().toString(), olatheResponse.getLat(), olatheResponse.getLon(), features);
        } catch (Exception ex) {
            log.error("exception:", ex);
            return null;
        }
    }

    @GET
    @Path("globeHour/{lat}/{lon}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GlobeResponse globeHourWeather(@PathParam("lat") String lat, @PathParam("lon") String lon) {
        log.info("globeHourWeather() lat:{}, lon:{}", lat, lon);
        try {
            Thread.sleep(500);
            OpenWeatherOneCallResponse response = null;
            OpenWeatherOneCallResponse olatheResponse = null;
            List<Feature> features = new ArrayList<>();
            response = weatherController.getWeatherFullByLatLon(lat, lon);
            log.info("globeWeather() response from database:{}", response);
            List<BigDecimal> coordinates = new ArrayList<>();
            coordinates.add(response.getLon());
            coordinates.add(response.getLat());
            Properties properties = new Properties(response.getPlace(), response.getLat(), response.getLon(), response.getCurrent().getTemp().toString(), Templates.globalWeatherCurrent(response).render());
            Geometry geometry = new Geometry("point", coordinates);
            Feature feature = new Feature(properties, geometry);
            features.add(feature);
            olatheResponse = response;
            log.info("globeHourWeather() completed for lat:{}, lon:{}", lat, lon);
            return new GlobeResponse(olatheResponse.getPlace(), olatheResponse.getCurrent().getTemp().toString(), olatheResponse.getLat(), olatheResponse.getLon(), features);
        } catch (Exception ex) {
            log.error("exception:", ex);
            return null;
        }
    }
}