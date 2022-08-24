package org.djr4488.quarkus;

import org.djr4488.quarkus.controller.WeatherController;
import org.djr4488.quarkus.model.onecall.OpenWeatherOneCallResponse;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("api/1.0")
public class WeatherResource {
    @Inject
    WeatherController weatherController;
    @Inject
    Logger log;

    @GET
    @Path("weather/current/{zipCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public String weather(@PathParam("zipCode") String zipCode) {
        return weatherController.getWeather(zipCode).toString();
    }

    @GET
    @Path("weather/full/{zipCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public OpenWeatherOneCallResponse weatherFull(@PathParam("zipCode") String zipCode) {
        OpenWeatherOneCallResponse response = weatherController.getFullWeather(zipCode);
        log.info("weatherFull() completed with response:{}", response);
        return response;
    }
}