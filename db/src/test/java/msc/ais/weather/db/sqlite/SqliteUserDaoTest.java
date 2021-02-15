package msc.ais.weather.db.sqlite;

import msc.ais.weather.db.DaoFactory;
import msc.ais.weather.db.UserDao;
import msc.ais.weather.model.db.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class SqliteUserDaoTest {

    @Disabled
    @Test
    public void testInsertUser() throws Exception {
//        User user = User.builder()
//            .firstName("Konstantinos")
//            .lastName("Raptis")
//            .email("kraptis@unipi.gr")
//            .password(new char[]{'a', 'b', 'c'})
//            .build();

        User user = User.builder()
            .firstName("Nikos")
            .lastName("Korobos")
            .email("nkorobos@gmail.com")
            .password(new char[]{'a', 'b', 'c'})
            .build();

        UserDao dao = DaoFactory.createUserDao();
        System.out.println("Insert User: " + dao.insertUser(user));
    }

    @Disabled
    @Test
    public void testFindUserByCredentials() throws Exception {

        UserDao userDao = DaoFactory.createUserDao();

        for (int i = 0; i < 10000; i++) {
            System.out.println("User: " + i + " "
                + userDao.findUserByCredentials("kraptis@test.com", new char[]{'a', 'b', 'c'}));
        }
    }

}
