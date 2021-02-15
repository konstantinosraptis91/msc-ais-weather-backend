package msc.ais.weather.service;

import msc.ais.weather.db.DaoFactory;
import msc.ais.weather.model.db.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class CityServiceImpl implements CityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

    /**
     * Find all user cities by given token id.
     *
     * @param id The token id
     * @return The cities
     */
    @Override
    public List<City> findCitiesByTokenId(String id) {
        return DaoFactory.createCityDao().findByUserTokenId(id);
    }

    /**
     * Find a city by given name in db or get it from geocoding API and store it in db.
     *
     * @param name The city name
     * @return The city
     */
    @Override
    public Optional<City> findCityByName(String name) {
        return DaoFactory.createCityDao().findByCityName(name)
            .or(() -> {
                LOGGER.debug("City with name: " + name + " not found in db."
                    + "Trying to get it from OpenWeatherMap and store it in db...");
                return ServiceFactory.createGeocodingService().getCityByName(name);
            });
    }
}
