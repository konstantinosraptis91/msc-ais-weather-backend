package msc.ais.weather.service;

import msc.ais.weather.db.DaoFactory;
import msc.ais.weather.db.UserCityDao;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 1/2/2021.
 */
public class UserCityServiceImpl implements UserCityService {

    /**
     * Delete a city from a user.
     *
     * @param tokenId The token id
     * @param cityId  The city id
     * @return The deletion result
     */
    @Override
    public boolean deleteUserCityByTokenId(String tokenId, int cityId) {
        UserCityDao userCityDao = DaoFactory.createUserCityDao();
        return userCityDao.deleteUserCityByTokenId(tokenId, cityId) == 1;
    }

    /**
     * Insert a city to a user.
     *
     * @param tokenId The token id
     * @param cityId  The city id
     * @return The insertion result
     */
    @Override
    public boolean insertUserCityByTokenId(String tokenId, int cityId) {
        UserCityDao userCityDao = DaoFactory.createUserCityDao();
        return userCityDao.insertUserCityByTokenId(tokenId, cityId) == 1;
    }
}
