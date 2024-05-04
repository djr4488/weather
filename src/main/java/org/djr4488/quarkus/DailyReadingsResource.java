package org.djr4488.quarkus;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.vertx.ext.web.RoutingContext;
import org.djr4488.quarkus.controller.DailyReadingsController;
import org.djr4488.quarkus.model.rss.RssResponse;
import org.slf4j.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("api/2.0")
public class DailyReadingsResource {
    @Inject
    RoutingContext context;

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance dailyReadings(RssResponse dailyReadings);
    }

    @Inject
    DailyReadingsController controller;
    @Inject
    Logger log;

    @Path("usccb/readings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RssResponse readings() {
        return controller.getReadings();
    }

    @Path("readings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public TemplateInstance dailyReadings() {
        return DailyReadingsResource.Templates.dailyReadings(controller.getReadings());
    }
}
