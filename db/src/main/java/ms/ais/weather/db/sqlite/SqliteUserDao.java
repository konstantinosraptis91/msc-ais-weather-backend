package ms.ais.weather.db.sqlite;

import ms.ais.weather.db.UserDao;
import ms.ais.weather.model.db.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class SqliteUserDao implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqliteUserDao.class);

    public enum Table {
        USER;

        public enum Column {
            USER_ID, USER_FIRSTNAME, USER_LASTNAME, USER_EMAIL, USER_PASSWORD
        }
    }

    @Override
    public int insertUser(User user) {

        final String query = "INSERT INTO " + Table.USER
            + " ("
            + Table.Column.USER_FIRSTNAME + ","
            + Table.Column.USER_LASTNAME + ","
            + Table.Column.USER_EMAIL + ","
            + Table.Column.USER_PASSWORD
            + ")"
            + " VALUES (?, ?, ?, ?)";

        int rowsAffected;
        int generatedKey = -1;

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection
                 .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, String.valueOf(user.getPassword()));
            rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected != 1) {
                throw new SQLException("rowsAffected: " + rowsAffected
                    + "Failed to insert user: " + user.toString());
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    generatedKey = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return generatedKey;
    }

    @Override
    public boolean deleteUserById(int id) {
        return false;
    }

    @Override
    public boolean findUserById(int id) {
        return false;
    }

    @Override
    public Optional<User> findUserByCredentials(String email, char[] password) {

        final String query = "SELECT * FROM " + Table.USER
            + " WHERE " + Table.Column.USER_EMAIL + "='" + email + "'"
            + " AND " + Table.Column.USER_PASSWORD + "='" + String.valueOf(password) + "'";

        User user = null;

        try (Connection connection = DBCPDataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                user = User.builder()
                    .userId(resultSet.getInt("user_id"))
                    .firstName(resultSet.getString("user_firstname"))
                    .lastName(resultSet.getString("user_lastname"))
                    .email(resultSet.getString("user_email"))
                    .password(resultSet.getString("user_password").toCharArray())
                    .build();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return Optional.ofNullable(user);
    }
}
