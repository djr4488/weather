package org.djr4488.quarkus.cdi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class LoggerProducer {
    @Produces
    public Logger produceLogger(InjectionPoint ip) {
        return LoggerFactory.getLogger(ip.getBean().getBeanClass());
    }
}
