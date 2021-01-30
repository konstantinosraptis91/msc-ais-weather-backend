package ms.ais.weather.db;

import ms.ais.weather.model.db.City;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public interface CityDao {

    int insertCity(City city) throws SQLException;

    List<City> findByUserId(int id) throws SQLException;

    Optional<City> findByCityName(String name) throws SQLException;

}
