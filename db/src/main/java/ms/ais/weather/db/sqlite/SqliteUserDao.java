package ms.ais.weather.db.sqlite;

import ms.ais.weather.db.UserDao;
import ms.ais.weather.model.db.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class SqliteUserDao implements UserDao {

    public enum Table {
        USER;

        public enum Column {
            USER_ID, USER_FIRSTNAME, USER_LASTNAME, USER_EMAIL, USER_PASSWORD
        }
    }

    @Override
    public int insertUser(User user) throws SQLException {

        final String query = "INSERT INTO " + Table.USER
            + " ("
            + Table.Column.USER_FIRSTNAME + ","
            + Table.Column.USER_LASTNAME + ","
            + Table.Column.USER_EMAIL + ","
            + Table.Column.USER_PASSWORD
            + ")"
            + " VALUES (?, ?, ?, ?)";

        int rowsAffected;

        try (PreparedStatement preparedStatement = DBCPDataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, String.valueOf(user.getPassword()));
            rowsAffected = preparedStatement.executeUpdate();
        }

        return rowsAffected;
    }

    @Override
    public boolean deleteUserById(int id) {
        return false;
    }

    @Override
    public boolean findUserById(int id) {
        return false;
    }
}
