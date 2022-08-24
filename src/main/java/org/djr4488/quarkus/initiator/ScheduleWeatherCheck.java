package org.djr4488.quarkus.initiator;

import io.quarkus.scheduler.Scheduled;
import org.djr4488.quarkus.controller.WeatherController;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

@ApplicationScoped
public class ScheduleWeatherCheck {
    @Inject
    protected Logger log;
    @Inject
    protected WeatherController weatherController;
    private final AtomicBoolean isExecuting = new AtomicBoolean(false);
    private LocalDateTime lastExecutedAt;

    @Scheduled(every = "1h")
    public void executeWeatherCheck() {
        log.info("executeWeatherCheck() started");
        if (!isExecuting.compareAndSet(false, true)) {
            lastExecutedAt = LocalDateTime.now();
            log.info("executeWeatherCheck() lastExecutedAt:{}", lastExecutedAt);
            weatherController.getWeather("66062");
            isExecuting.set(false);
        }
        log.info("executeWeatherCheck() completed");
    }

    public LocalDateTime getLastExecutedAt() {
        return lastExecutedAt;
    }
}
