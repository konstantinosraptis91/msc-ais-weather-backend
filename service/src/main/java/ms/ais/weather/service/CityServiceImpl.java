package ms.ais.weather.service;

import ms.ais.weather.db.CityDao;
import ms.ais.weather.db.DaoFactory;
import ms.ais.weather.model.db.City;
import ms.ais.weather.model.location.CityGeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class CityServiceImpl implements CityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

    @Override
    public List<City> findCitiesByTokenId(String id) {
        return null;
    }

    @Override
    public Optional<City> findCityByName(String name) {

        CityDao cityDao = DaoFactory.createCityDao();

        try {
            Optional<City> oCity = cityDao.findByCityName(name);
            if (oCity.isPresent()) {
                LOGGER.debug("City with name: " + name + " found in db.");
                return oCity;
            } else {
                LOGGER.debug("City with name: " + name + " not found in db."
                    + "Trying to get it from OpenWeatherMap and store it in db...");

                GeocodingService geocodingService = ServiceFactory.createGeocodingService();
                return geocodingService.getCityByName(name);
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return Optional.empty();
    }
}
