package ms.ais.weather.db;

import ms.ais.weather.model.db.Token;

import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public interface TokenDao {

    int insertToken(Token token) throws SQLException;

    Optional<Token> findTokenById(String id) throws SQLException;

    int deleteTokenById(String id);

}
