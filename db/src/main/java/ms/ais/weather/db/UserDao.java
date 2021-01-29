package ms.ais.weather.db;

import ms.ais.weather.model.auth.User;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public interface UserDao {

    boolean insertUser(User user);

    boolean deleteUserById(int id);

    boolean findUserById(int id);

}
