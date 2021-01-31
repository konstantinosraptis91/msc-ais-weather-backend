package ms.ais.weather.service;

import ms.ais.weather.model.db.User;

import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public interface UserService {

    Optional<String> signUp(User user);

    Optional<String> singIn(String email, char[] password);

    boolean signOut(String tokenId);
}
