package org.djr4488.quarkus.services.database;

import org.apache.commons.lang3.StringUtils;
import org.djr4488.quarkus.model.store.WeatherLocation;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

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

    public <T> T save(T entity) {
        em.persist(entity);
        return entity;
    }
}
