package ms.ais.weather.db.sqlite;

import ms.ais.weather.db.DaoFactory;
import ms.ais.weather.db.UserDao;
import ms.ais.weather.model.db.User;
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

}
