package org.djr4488.quarkus.initiator;

import io.quarkus.scheduler.Scheduled;
import org.djr4488.quarkus.controller.WeatherController;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class GlobalScheduler {
    @Inject
    WeatherController weatherController;
    @Inject
    Logger log;

    public static final List<String> zipList = Arrays.asList("66062", "80005", "80017", "67212", "90210", "96712", "79101",
            "92037", "98101", "97035", "00120,va", "75003,fr", "E14,gb", "1000000,jp", "33101", "32301", "37501", "10001",
            "63101", "00901", "2055,au", "66502", "1150,nz", "67601", "67701", "80828", "84765");

    @Scheduled(every = "2h")
    public void updateGlobalWeatherInterestAreas() {
        for (String zip : zipList) {
            log.trace("updateGlobaleWeatherInterestAreas() weather:{}", weatherController.getFullWeather(zip));
        }
    }
}
