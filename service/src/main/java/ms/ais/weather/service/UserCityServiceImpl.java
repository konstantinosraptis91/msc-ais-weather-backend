package ms.ais.weather.service;

import ms.ais.weather.db.DaoFactory;
import ms.ais.weather.db.UserCityDao;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 1/2/2021.
 */
public class UserCityServiceImpl implements UserCityService {

    @Override
    public boolean deleteUserCityByTokenId(String tokenId, int cityId) {
        UserCityDao userCityDao = DaoFactory.createUserCityDao();
        return userCityDao.deleteUserCityByTokenId(tokenId, cityId) == 1;
    }

    @Override
    public boolean insertUserCityByTokenId(String tokenId, int cityId) {
        UserCityDao userCityDao = DaoFactory.createUserCityDao();
        return userCityDao.insertUserCityByTokenId(tokenId, cityId) == 1;
    }
}
