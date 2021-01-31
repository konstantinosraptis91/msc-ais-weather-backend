package ms.ais.weather.service;

import ms.ais.weather.model.db.City;

import java.util.List;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public interface CityService {

    List<City> findCitiesByTokenId(String id);

    Optional<City> findCityByName(String name);
}
