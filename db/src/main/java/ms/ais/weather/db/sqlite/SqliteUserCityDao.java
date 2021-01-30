package ms.ais.weather.db.sqlite;

import ms.ais.weather.db.UserCityDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class SqliteUserCityDao implements UserCityDao {

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

        try (PreparedStatement preparedStatement = DBCPDataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, cityId);
            preparedStatement.setInt(2, userId);
            rowsAffected = preparedStatement.executeUpdate();
        }

        return rowsAffected;
    }

}
