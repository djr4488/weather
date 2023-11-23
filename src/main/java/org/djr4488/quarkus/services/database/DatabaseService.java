package org.djr4488.quarkus.services.database;

import org.apache.commons.lang3.StringUtils;
import org.djr4488.quarkus.model.store.WeatherData;
import org.djr4488.quarkus.model.store.WeatherLocation;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
@Transactional(Transactional.TxType.MANDATORY)
public class DatabaseService {
    @PersistenceContext
    EntityManager em;

    public WeatherLocation findWeatherLocationByZipCode(String zipCode) {
        try {
            if (StringUtils.trimToNull(zipCode) != null) {
                TypedQuery<WeatherLocation> query = em.createNamedQuery("findWeatherLocationByZipCode", WeatherLocation.class);
                query.setParameter("zipCode", zipCode);
                return query.getSingleResult();
            } else {
                return null;
            }
        } catch (NoResultException nrEx) {
            return null;
        }
    }

    public WeatherData findMostRecentWeatherDataForLocation(String location) {
        System.out.println("location:" + location);
        try {
            if (StringUtils.trimToNull(location) != null) {
                TypedQuery<WeatherData> query = em.createNamedQuery("findMostRecentWeatherDataForLocation", WeatherData.class);
                query.setParameter("location", location);
                query.setParameter("offsetDateTime", LocalDateTime.now().minusHours(2));
                return query.getSingleResult();
            } else {
                return null;
            }
        } catch (NoResultException nrEx) {
            return null;
        }
    }

    public WeatherLocation findWeatherLocationByLatLon(String lat, String lon) {
        try {
            TypedQuery<WeatherLocation> query = em.createNamedQuery("findWeatherLocationByLatLon", WeatherLocation.class);
            query.setParameter("lat", lat);
            query.setParameter("lon", lon);
            return query.getSingleResult();
        } catch (NoResultException nrEx) {
            return null;
        }
    }

    public Set<String> findAllDistinctWeatherLocations() {
        try {
            TypedQuery<String> query = em.createNamedQuery("findAllDistinctWeatherLocations", String.class);
            return new HashSet<String>(query.getResultList());
        } catch (NoResultException nrEx) {
            return null;
        }
    }

    public <T> T save(T entity) {
        em.persist(entity);
        return entity;
    }
}
