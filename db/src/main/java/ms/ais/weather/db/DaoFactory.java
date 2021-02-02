package ms.ais.weather.db;

import ms.ais.weather.db.sqlite.*;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class DaoFactory {

    public static UserDao createUserDao() {
        return new SqliteUserDao();
    }

    public static CityDao createCityDao() {
        return new SqliteCityDao();
    }

    public static UserCityDao createUserCityDao() {
        return new SqliteUserCityDao();
    }

    public static TokenDao createTokenDao() {
        return new SqliteTokenDao();
    }

    public static AliasDao createAliasDao() {
        return new SqliteAliasDao();
    }

}
