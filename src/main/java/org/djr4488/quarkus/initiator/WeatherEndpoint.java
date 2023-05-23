package org.djr4488.quarkus.initiator;

import org.apache.camel.BindToRegistry;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.djr4488.quarkus.controller.WeatherController;
import org.djr4488.quarkus.model.onecall.OpenWeatherOneCallResponse;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class WeatherEndpoint extends RouteBuilder {
    @Inject
    Logger log;

    @BindToRegistry
    @Inject
    WeatherController weatherController;

    @Override
    public void configure() {
        restConfiguration()
                .skipBindingOnErrorCode(false);

        rest("/weather/camel/1.0")
                .id("weatherZipEndpoint")
                .produces("application/json")
                .get("/{zip}")
                    .type(String.class)
                    .outType(OpenWeatherOneCallResponse.class)
                .to("direct:weatherResponse");

        from("direct:weatherResponse")
                .to("bean:weatherController?method=getFullWeather(${header.zip})")
                .log(LoggingLevel.DEBUG, log, "${body}")
                .marshal().json(JsonLibrary.Jsonb, OpenWeatherOneCallResponse.class);
    }
}
