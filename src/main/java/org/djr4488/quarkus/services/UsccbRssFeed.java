package org.djr4488.quarkus.services;

import org.djr4488.quarkus.model.rss.RssResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@RegisterRestClient
public interface UsccbRssFeed {
    @GET
    @Path("readings.rss")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML+";charset=UTF-8" })
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
    public String readings();
}
