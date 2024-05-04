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
public interface CatholicNewsClient {
    /** @GET
    @Path("news.rss")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML+";charset=UTF-8" })
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
    public RssResponse catholicNews();


    @GET
    @Path("catholicnewsagency/dailynews-vatican")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML+";charset=UTF-8" })
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
    public RssResponse dailyVatican();


    @GET
    @Path("catholicnewsagency/dailynews-us")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML+";charset=UTF-8" })
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
    public RssResponse dailyUS();
     **/

   @GET
    @Path("catholicnewsagency/dailynews")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML+";charset=UTF-8" })
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
    public RssResponse dailyGeneral();

     /** @GET
    @Path("catholicnewsagency/dailynews-americas")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML+";charset=UTF-8" })
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
    public RssResponse dailyAmericas();

    @GET
    @Path("catholicnewsagency/dailynews-europe")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML+";charset=UTF-8" })
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
    public RssResponse dailyEurope();

    @GET
    @Path("catholicnewsagency/dailynews-asia")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML+";charset=UTF-8" })
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
    public RssResponse dailyAsia();

    @GET
    @Path("catholicnewsagency/dailynews-middleeast")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML+";charset=UTF-8" })
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
    public RssResponse dailyMiddleEast();
    **/

    @GET
    @Path("catholicnewsagency/saintoftheday")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML+";charset=UTF-8" })
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
    public RssResponse saintOfTheDay();
}

