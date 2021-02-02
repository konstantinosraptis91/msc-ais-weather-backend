package ms.ais.weather.db;

import ms.ais.weather.model.db.User;

import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public interface UserDao {

    int insertUser(User user);

    boolean deleteUserById(int id);

    boolean findUserById(int id);

    Optional<User> findUserByCredentials(String email, char[] password);
}
