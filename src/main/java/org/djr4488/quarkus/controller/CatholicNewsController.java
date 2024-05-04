package org.djr4488.quarkus.controller;

import org.djr4488.quarkus.model.rss.Item;
import org.djr4488.quarkus.model.rss.RssResponse;
import org.djr4488.quarkus.services.CatholicNewsClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class CatholicNewsController {
    @Inject
    @RestClient
    CatholicNewsClient client;

    @Inject
    Logger log;

    public RssResponse getNews() {
        final RssResponse response = client.dailyGeneral();
        final RssResponse sotd = client.saintOfTheDay();
        response.getChannel().getItem().addAll(sotd.getChannel().getItem());
        List<Item> sortedByCategory = response.itemSortByCategory();
        log.trace("getNews() response:{}", response);
        response.getChannel().setItem(sortedByCategory);
        return response;
    }
}
