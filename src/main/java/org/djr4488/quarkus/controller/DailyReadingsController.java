package org.djr4488.quarkus.controller;

import org.djr4488.quarkus.model.rss.RssResponse;
import org.djr4488.quarkus.services.UsccbRssFeed;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class DailyReadingsController {
    @Inject
    Logger log;
    @Inject
    @RestClient
    UsccbRssFeed usccbRssFeed;

    public RssResponse getReadings() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RssResponse.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (RssResponse)jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(usccbRssFeed.readings().getBytes("UTF-8")));
            /** final RssResponse response = usccbRssFeed.readings();
            log.trace("getReadings() response:{}", response);
            return response; **/
        } catch (Exception ex) {
            log.error("exception:", ex);
            return null;
        }
    }
}
