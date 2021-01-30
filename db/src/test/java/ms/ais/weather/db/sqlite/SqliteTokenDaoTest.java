package ms.ais.weather.db.sqlite;

import ms.ais.weather.db.DaoFactory;
import ms.ais.weather.db.TokenDao;
import ms.ais.weather.model.db.Token;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/1/2021.
 */
public class SqliteTokenDaoTest {

    @Test
    public void testInsertToken() throws Exception {

        final String tokenId = UUID.randomUUID().toString();

        Token token = Token.builder()
            .tokenId(tokenId)
            .userId(1)
            .build();

        TokenDao dao = DaoFactory.createTokenDao();
        System.out.println("Insert token: " + dao.insertToken(token));
    }

    @Test
    public void testFindTokenById() throws Exception {

        TokenDao dao = DaoFactory.createTokenDao();
        System.out.println(dao.findTokenById("cd804bc0-b300-4401-99a8-194b8d4a8103"));
    }

}