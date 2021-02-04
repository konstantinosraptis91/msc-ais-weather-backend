package ms.ais.weather.db.sqlite;

import ms.ais.weather.db.CityDao;
import ms.ais.weather.db.exception.DataException;
import ms.ais.weather.model.db.City;
import ms.ais.weather.model.location.CityGeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class SqliteCityDao implements CityDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqliteCityDao.class);

    public enum Table {
        CITY;

        public enum Column {
            CITY_ID, CITY_NAME, CITY_LONGITUDE, CITY_LATITUDE, CITY_COUNTRY
        }
    }

    @Override
    public int insertCity(City city) {

        LOGGER.debug("Inserting city: " + city.toString() + " to db...");

        final String query = "INSERT INTO " + Table.CITY
            + " ("
            + Table.Column.CITY_NAME + ","
            + Table.Column.CITY_LONGITUDE + ","
            + Table.Column.CITY_LATITUDE + ","
            + Table.Column.CITY_COUNTRY
            + ")"
            + " VALUES (?, ?, ?, ?)";

        int rowsAffected = -1;
        int generatedKey = -1;

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection
                 .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, city.getCityGeoPoint().getCityName());
            preparedStatement.setDouble(2, city.getCityGeoPoint().getLongitude());
            preparedStatement.setDouble(3, city.getCityGeoPoint().getLatitude());
            preparedStatement.setString(4, city.getCountry());
            rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                LOGGER.debug("City: " + city.toString() + " inserted successfully in db!!!");
            } else {
                throw new DataException("City: " + city.toString()
                    + " failed to be inserted in db! (Possibly already stored)");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    generatedKey = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (DataException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.debug("Returning generated key: " + generatedKey);

        return generatedKey;
    }

    @Override
    public List<City> findByUserId(int id) {

        final String query = "SELECT ct." + Table.Column.CITY_ID + ","
            + "ct." + Table.Column.CITY_NAME + ","
            + "ct." + Table.Column.CITY_LONGITUDE + ","
            + "ct." + Table.Column.CITY_LATITUDE + ","
            + "ct." + Table.Column.CITY_COUNTRY
            + " FROM " + Table.CITY + " ct INNER JOIN " + SqliteUserCityDao.Table.USER_CITY + " uct "
            + "ON ct." + Table.Column.CITY_ID + "=" + "uct." + SqliteUserCityDao.Table.Column.CITY_ID
            + " WHERE uct." + SqliteUserCityDao.Table.Column.USER_ID + "=" + id;

        return findCitiesByQuery(query);
    }

    @Override
    public OptionalInt findCityIdByNameOrAlias(String nameOrAlias) {

        final String query = "SELECT city_id"
            + " FROM (SELECT city_id, city_name FROM city"
            + " UNION"
            + " SELECT city_id, alias_name FROM alias)"
            + " WHERE city_name = '" + nameOrAlias + "'";

        OptionalInt oCityId = OptionalInt.empty();

        try (Connection connection = DBCPDataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                oCityId = OptionalInt.of(resultSet.getInt(Table.Column.CITY_ID.name()));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return oCityId;
    }

    @Override
    public Optional<City> findByCityName(String name) {

//        final String query = "SELECT"
//            + " ct.city_id, ct.city_name, ct.city_longitude, ct.city_latitude, ct.city_country"
//            + " FROM city ct"
//            + " WHERE ct.city_name = '" + name + "'";

        final String query = "SELECT"
            + " ct.city_id, ct.city_name, ct.city_longitude, ct.city_latitude, ct.city_country"
            + " FROM city ct LEFT JOIN alias a ON ct.city_id = a.city_id"
            + " WHERE ct.city_name = '" + name + "' OR a.alias_name = '" + name + "'"
            + " LIMIT 1";

        City city = null;

        try (Connection connection = DBCPDataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                city = City.builder()
                    .cityId(resultSet.getInt(Table.Column.CITY_ID.name()))
                    .cityGeoPoint(CityGeoPoint.builder()
                        .withCityName(resultSet.getString(Table.Column.CITY_NAME.name()))
                        .withLongitude(resultSet.getDouble(Table.Column.CITY_LONGITUDE.name()))
                        .withLatitude(resultSet.getDouble(Table.Column.CITY_LATITUDE.name()))
                        .build())
                    .country(resultSet.getString(Table.Column.CITY_COUNTRY.name()))
                    .build();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return Optional.ofNullable(city);
    }

    @Override
    public List<City> findByUserTokenId(String tokenId) {

        final String query = "SELECT "
            + " ct.city_id, ct.city_name, ct.city_longitude, ct.city_latitude, ct.city_country"
            + " FROM city ct"
            + " INNER JOIN user_city uc ON ct.city_id = uc.city_id"
            + " INNER JOIN token t ON uc.user_id = t.user_id"
            + " WHERE t.token_id = '" + tokenId + "'";

        return findCitiesByQuery(query);
    }

    private List<City> findCitiesByQuery(String query) {

        final List<City> cityList = new ArrayList<>();

        try (Connection connection = DBCPDataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                City city = City.builder()
                    .cityId(resultSet.getInt(Table.Column.CITY_ID.name()))
                    .cityGeoPoint(CityGeoPoint.builder()
                        .withCityName(resultSet.getString(Table.Column.CITY_NAME.name()))
                        .withLongitude(resultSet.getDouble(Table.Column.CITY_LONGITUDE.name()))
                        .withLatitude(resultSet.getDouble(Table.Column.CITY_LATITUDE.name()))
                        .build())
                    .country(resultSet.getString(Table.Column.CITY_COUNTRY.name()))
                    .build();
                cityList.add(city);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return cityList;
    }
}
