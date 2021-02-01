package ms.ais.weather.db.sqlite;

import ms.ais.weather.db.DaoFactory;
import ms.ais.weather.db.UserCityDao;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/1/2021.
 */
public class SqliteUserCityDaoTest {

    @Disabled
    @Test
    public void testInsertUserCity() throws Exception {

        UserCityDao dao = DaoFactory.createUserCityDao();
        System.out.println("Insert UserCity: " + dao.insertUserCity(2, 1));
        System.out.println("Insert UserCity: " + dao.insertUserCity(2, 2));
        System.out.println("Insert UserCity: " + dao.insertUserCity(2, 5));
        System.out.println("Insert UserCity: " + dao.insertUserCity(2, 6));
        System.out.println("Insert UserCity: " + dao.insertUserCity(2, 8));
    }

}
