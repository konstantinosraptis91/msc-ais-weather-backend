package ms.ais.weather.db.sqlite;

import ms.ais.weather.db.UserCityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class SqliteUserCityDao implements UserCityDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqliteUserCityDao.class);

    public enum Table {
        USER_CITY;

        public enum Column {
            USER_ID, CITY_ID
        }
    }

    @Override
    public int insertUserCity(int userId, int cityId) throws SQLException {

        final String query = "INSERT INTO " + Table.USER_CITY
            + " ("
            + Table.Column.CITY_ID + ","
            + Table.Column.USER_ID
            + ")"
            + " VALUES (?,?)";

        int rowsAffected;

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cityId);
            preparedStatement.setInt(2, userId);
            rowsAffected = preparedStatement.executeUpdate();
        }

        return rowsAffected;
    }

    @Override
    public int insertUserCityByTokenId(String tokenId, int cityId) {

        final String query = "INSERT INTO user_city (city_id, user_id)"
            + " VALUES (?,"
            + " (SELECT u.user_id FROM user u"
            + " INNER JOIN token t ON u.user_id = t.user_id"
            + " WHERE t.token_id = ?))";

        int rowsAffected = -1;

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cityId);
            preparedStatement.setString(2, tokenId);
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return rowsAffected;
    }

    @Override
    public int deleteUserCityByTokenId(String tokenId, int cityId) {

        final String query = "DELETE"
            + " FROM user_city"
            + " WHERE (SELECT u.user_id FROM user u INNER JOIN token t ON u.user_id = t.user_id"
            + " WHERE t.token_id = '" + tokenId + "')"
            + " AND city_id = ?";

        int rowsAffected = -1;

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cityId);
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return rowsAffected;
    }
}
