package ms.ais.weather.db.sqlite;

import ms.ais.weather.db.DaoFactory;
import ms.ais.weather.db.TokenDao;
import ms.ais.weather.model.db.Token;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/1/2021.
 */
public class SqliteTokenDaoTest {

    @Disabled
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

    @Disabled
    @Test
    public void testFindTokenById() throws Exception {

        TokenDao dao = DaoFactory.createTokenDao();
        System.out.println(dao.findTokenById("cd804bc0-b300-4401-99a8-194b8d4a8103"));
    }

    @Disabled
    @Test
    public void testDeleteTokenById() throws Exception {

        TokenDao dao = DaoFactory.createTokenDao();
        System.out.println("Delete token: " + dao.deleteTokenById("3c331cc2-23f1-4db9-96ad-aa3a23abdebc"));
    }

}
