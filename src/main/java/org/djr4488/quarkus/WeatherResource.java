package org.djr4488.quarkus;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.djr4488.quarkus.controller.WeatherController;
import org.djr4488.quarkus.model.onecall.OpenWeatherOneCallResponse;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("api/2.0")
public class WeatherResource {

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance weather(OpenWeatherOneCallResponse weather);
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
    @Path("weather")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TemplateInstance weather(@QueryParam("zipCode") String zipCode) {
        OpenWeatherOneCallResponse response = weatherController.getFullWeather(zipCode);
        log.info("weather() completed with response.place:{}", response.getPlace());
        return Templates.weather(response);
    }
}