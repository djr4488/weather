package org.djr4488.quarkus.services.database;

import org.apache.commons.lang3.StringUtils;
import org.djr4488.quarkus.model.store.Tile;
import org.djr4488.quarkus.model.store.Track;
import org.djr4488.quarkus.model.store.WeatherData;
import org.djr4488.quarkus.model.store.WeatherLocation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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
                query.setParameter("offsetDateTime", LocalDateTime.now().toLocalDate().atStartOfDay());
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

    public List<Track> findTracksSince(final LocalDateTime localDateTime) {
        TypedQuery<Track> query = em.createNamedQuery("findTracksSince", Track.class);
        query.setParameter("localDateTime", localDateTime);
        return query.getResultList();
    }

    public Tile findTileBySXYZ(final String s, final Integer x, final Integer y, final Integer z) {
        try {
            TypedQuery<Tile> query = em.createNamedQuery("findTileBySXYZ", Tile.class);
            query.setParameter("s", s);
            query.setParameter("x", x);
            query.setParameter("y", y);
            query.setParameter("z", z);
            return query.getSingleResult();
        } catch (NoResultException nrEx) {
            return null;
        }
    }

    public <T> T save(T entity) {
        em.persist(entity);
        return entity;
    }
}
