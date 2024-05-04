package org.djr4488.quarkus;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.vertx.ext.web.RoutingContext;
import org.djr4488.quarkus.controller.CatholicNewsController;
import org.djr4488.quarkus.model.rss.RssResponse;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("api/2.0")
public class CatholicNewsResource {
    @Inject
    RoutingContext context;

    @Inject
    CatholicNewsController controller;

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance catholicNews(RssResponse catholicNews);
    }

    @Path("catholicNews")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public TemplateInstance catholicNews() {
        return CatholicNewsResource.Templates.catholicNews(controller.getNews());
    }
}
