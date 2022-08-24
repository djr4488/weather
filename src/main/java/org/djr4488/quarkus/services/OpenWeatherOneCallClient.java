package org.djr4488.quarkus.services;

import org.djr4488.quarkus.model.onecall.OpenWeatherOneCallResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
