package ms.ais.weather.service;

import ms.ais.weather.db.DaoFactory;
import ms.ais.weather.db.TokenDao;
import ms.ais.weather.db.UserDao;
import ms.ais.weather.model.db.Token;
import ms.ais.weather.model.db.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 31/1/2021.
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Optional<String> signUp(User user) {

        UserDao userDao = DaoFactory.createUserDao();
        String tokenId = null;

        LOGGER.debug("Trying to Sign up user: " + user.toString());

        int userId = userDao.insertUser(user);
        if (userId != -1) {
            LOGGER.debug("User: " + user.toString() + " inserted successfully!!! User id: " + userId);

            TokenDao tokenDao = DaoFactory.createTokenDao();
            tokenId = UUID.randomUUID().toString();
            Token token = Token.builder()
                .userId(userId)
                .tokenId(tokenId)
                .build();

            int rowsAffected = tokenDao.insertToken(token);
            if (rowsAffected == 1) {
                LOGGER.debug("Token: " + token.toString() + " inserted successfully!!!");
            }
        }

        return Optional.ofNullable(tokenId);
    }

    @Override
    public Optional<String> singIn(String email, char[] password) {

        String tokenId = null;

        LOGGER.debug("Trying to Sign in user with email: " + email + ", password: " + String.valueOf(password));
        UserDao userDao = DaoFactory.createUserDao();
        Optional<User> oUser = userDao.findUserByCredentials(email, password);

        if (oUser.isPresent()) {
            TokenDao tokenDao = DaoFactory.createTokenDao();
            Token token = Token.builder()
                .tokenId(UUID.randomUUID().toString())
                .userId(oUser.get().getId())
                .build();

            int rowsAffected = tokenDao.insertToken(token);
            if (rowsAffected == 1) {
                tokenId = token.getId();
                LOGGER.debug("User with email: " + email + ", password: " + String.valueOf(password)
                    + " signed in successfully!!!");
            }
        } else {
            LOGGER.debug("Unable to find user with email: " + email + " and password: " + String.valueOf(password));
        }

        return Optional.ofNullable(tokenId);
    }

    @Override
    public boolean signOut(String tokenId) {

        TokenDao tokenDao = DaoFactory.createTokenDao();
        boolean isDeleted = false;

        LOGGER.debug("Trying to delete token with id: " + tokenId);
        int rowsAffected = tokenDao.deleteTokenById(tokenId);
        if (rowsAffected == 1) {
            isDeleted = true;
            LOGGER.debug("Token with id: " + tokenId + " deleted successfully!!!");
        }

        return isDeleted;
    }
}
