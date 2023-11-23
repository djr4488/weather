package org.djr4488.quarkus.services;

import org.djr4488.quarkus.model.geocode.OpenWeatherGeocodeResponse;
import org.djr4488.quarkus.model.openweather.OpenWeatherResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

//"coord":{"latitude":38.8733,"longitude":-94.7752}
@ApplicationScoped
@Path("/geo/1.0")
@RegisterRestClient
public interface OpenWeatherGeocodeClient {
    @GET
    @Path("/zip")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OpenWeatherGeocodeResponse getGeocode(@QueryParam("zip") String zip,
                                               @QueryParam("appid") String apiKey);

    /**
     * http://api.openweathermap.org/geo/1.0/reverse?lat={lat}&lon={lon}&limit={limit}&appid={API key}
     */
    @GET
    @Path("/reverse")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<OpenWeatherGeocodeResponse> getGeocode(@QueryParam("lat") String lat,
                                                       @QueryParam("lon") String lon,
                                                       @QueryParam("limit") String limit,
                                                       @QueryParam("appid") String apiKey);
}
