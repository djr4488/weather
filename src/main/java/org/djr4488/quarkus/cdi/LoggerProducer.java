package org.djr4488.quarkus.cdi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class LoggerProducer {
    @Produces
    public Logger produceLogger(InjectionPoint ip) {
        return LoggerFactory.getLogger(ip.getBean().getBeanClass());
    }
}
