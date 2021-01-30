package ms.ais.weather.service;

import ms.ais.weather.model.db.User;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public interface UserService {

    String signUp(User user);

    String singIn(String username, char[] password);

}
