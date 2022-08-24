package org.djr4488.quarkus.services;

import org.djr4488.quarkus.model.openweather.OpenWeatherResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/data/2.5")
@RegisterRestClient
public interface OpenWeatherMapClient {
    @GET
    @Path("/weather")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OpenWeatherResponse getWeatherByZip(@QueryParam("zip") String zip, @QueryParam("appid") String apiKey,
                                               @QueryParam("units") String units);
}
