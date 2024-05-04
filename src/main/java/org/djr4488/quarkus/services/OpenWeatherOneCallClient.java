package org.djr4488.quarkus.services;

import org.djr4488.quarkus.model.onecall.OpenWeatherOneCallResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/data/3.0")
@RegisterRestClient
public interface OpenWeatherOneCallClient {
    @GET
    @Path("onecall")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OpenWeatherOneCallResponse getOneCallResponse(@QueryParam("lat") String lat,
                                                  @QueryParam("lon") String lon,
                                                  @QueryParam("appid") String appid,
                                                  @QueryParam("units") String units);
}
