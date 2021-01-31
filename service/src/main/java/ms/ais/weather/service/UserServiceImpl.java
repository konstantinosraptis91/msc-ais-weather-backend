package ms.ais.weather.service;

import ms.ais.weather.db.DaoFactory;
import ms.ais.weather.db.TokenDao;
import ms.ais.weather.db.UserDao;
import ms.ais.weather.model.db.Token;
import ms.ais.weather.model.db.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
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
        String tokenId;

        try {
            int userId = userDao.insertUser(user);
            LOGGER.debug("User: " + user.toString() + " inserted successfully!!! User id: " + userId);

            TokenDao tokenDao = DaoFactory.createTokenDao();
            tokenId = UUID.randomUUID().toString();
            Token token = Token.builder()
                .userId(userId)
                .tokenId(tokenId)
                .build();

            tokenDao.insertToken(token);
            LOGGER.debug("Token: " + token.toString() + " inserted successfully!!!");

        } catch (SQLException e) {
            LOGGER.error("Failed to signUp user: " + user.toString(), e);
            return Optional.empty();
        }

        return Optional.ofNullable(tokenId);
    }

    @Override
    public Optional<String> singIn(String email, char[] password) {

        TokenDao tokenDao = DaoFactory.createTokenDao();
        String tokenId = null;

        try {
            LOGGER.debug("Trying to Sign in user with email: " + email + ", password: " + String.valueOf(password));
            Optional<Token> oToken = tokenDao.findTokenByUserCredentials(email, password);

            if (oToken.isPresent()) {
                tokenId = oToken.get().getId();
                LOGGER.debug("User with email: " + email + ", password: " + String.valueOf(password)
                    + " signed in successfully!!!");
            } else {
                throw new SQLException("Failed to signIn user with email: "
                    + email + ", password: " + String.valueOf(password));
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return Optional.ofNullable(tokenId);
    }

    @Override
    public boolean signOut(String tokenId) {

        TokenDao tokenDao = DaoFactory.createTokenDao();
        boolean isDeleted = false;

        try {
            LOGGER.debug("Trying to delete token with id: " + tokenId);
            int rowsAffected = tokenDao.deleteTokenById(tokenId);
            if (rowsAffected == 1) {
                isDeleted = true;
                LOGGER.debug("Token with id: " + tokenId + " deleted successfully!!!");
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return isDeleted;
    }
}
