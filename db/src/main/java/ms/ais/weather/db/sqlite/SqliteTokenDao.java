package ms.ais.weather.db.sqlite;

import ms.ais.weather.db.TokenDao;
import ms.ais.weather.model.db.Token;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/1/2021.
 */
public class SqliteTokenDao implements TokenDao {

    public enum Table {
        TOKEN;

        public enum Column {
            TOKEN_ID, USER_ID, TOKEN_CREATED_TIMESTAMP
        }
    }

    @Override
    public int insertToken(Token token) throws SQLException {

        final String query = "INSERT INTO " + Table.TOKEN
            + " ("
            + Table.Column.TOKEN_ID + ","
            + Table.Column.USER_ID
            + ")"
            + " VALUES (?,?)";

        int rowsAffected;

        try (PreparedStatement preparedStatement = DBCPDataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, token.getId());
            preparedStatement.setInt(2, token.getUserId());
            rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected != 1) {
                throw new SQLException("Failed to insert token: " + token.toString());
            }
        }

        return rowsAffected;
    }

    @Override
    public Optional<Token> findTokenById(String id) throws SQLException {

        final String query = "SELECT *"
            + " FROM " + Table.TOKEN
            + " WHERE " + Table.Column.TOKEN_ID + "=" + "'" + id + "'";

        return findTokenByQuery(query);
    }

    @Override
    public Optional<Token> findTokenByUserCredentials(String email, char[] password) throws SQLException {

        final String query = "SELECT tk." + Table.Column.TOKEN_ID + ","
            + "tk." + Table.Column.USER_ID + ","
            + "tk." + Table.Column.TOKEN_CREATED_TIMESTAMP
            + " FROM " + Table.TOKEN + " tk INNER JOIN " + SqliteUserDao.Table.USER + " usr"
            + " ON tk." + Table.Column.USER_ID + "=" + "usr." + SqliteUserDao.Table.Column.USER_ID
            + " WHERE usr." + SqliteUserDao.Table.Column.USER_EMAIL + "=" + "'" + email + "'"
            + " AND usr." + SqliteUserDao.Table.Column.USER_PASSWORD + "=" + "'" + String.valueOf(password) + "'";

        return findTokenByQuery(query);
    }

    private Optional<Token> findTokenByQuery(String query) throws SQLException {

        Token token = null;
        Statement statement = DBCPDataSource.getConnection().createStatement();
        try (ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {

                token = Token.builder()
                    .tokenId(resultSet.getString(Table.Column.TOKEN_ID.name()))
                    .userId(resultSet.getInt(Table.Column.USER_ID.name()))
                    .createdTimestamp(resultSet.getLong(Table.Column.TOKEN_CREATED_TIMESTAMP.name()))
                    .build();
            }
        }

        return Optional.ofNullable(token);
    }

    @Override
    public int deleteTokenById(String id) throws SQLException {

        final String query = "DELETE FROM " + Table.TOKEN
            + " WHERE " + Table.Column.TOKEN_ID + "= ?";

        int rowsAffected;

        try (PreparedStatement preparedStatement = DBCPDataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, id);
            rowsAffected = preparedStatement.executeUpdate();
        }

        return rowsAffected;
    }
}
