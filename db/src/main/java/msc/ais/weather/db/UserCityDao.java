package msc.ais.weather.db;

import java.sql.SQLException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public interface UserCityDao {

    int insertUserCity(int userId, int cityId);

    int deleteUserCityByTokenId(String tokenId, int cityId);

    int insertUserCityByTokenId(String tokenId, int cityId);
}
