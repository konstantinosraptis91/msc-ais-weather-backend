package msc.ais.weather.db.sqlite;

import msc.ais.weather.db.AliasDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 2/2/2021.
 */
public class SqliteAliasDao implements AliasDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqliteAliasDao.class);

    @Override
    public int insertAlias(int id, String name) {

        final String query = "INSERT INTO alias (alias_name, city_id) VALUES (?, ?)";
        int rowsAffected = -1;

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return rowsAffected;
    }
}
