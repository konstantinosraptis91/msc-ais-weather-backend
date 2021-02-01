package ms.ais.weather.db.sqlite;

import ms.ais.weather.db.CityDao;
import ms.ais.weather.model.db.City;
import ms.ais.weather.model.location.CityGeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public int insertCity(City city) throws SQLException {

        LOGGER.debug("Inserting city: " + city.toString() + " to db...");

        final String query = "INSERT INTO " + Table.CITY
            + " ("
            + Table.Column.CITY_NAME + ","
            + Table.Column.CITY_LONGITUDE + ","
            + Table.Column.CITY_LATITUDE + ","
            + Table.Column.CITY_COUNTRY
            + ")"
            + " VALUES (?, ?, ?, ?)";

        int rowsAffected;

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, city.getCityGeoPoint().getCityName());
            preparedStatement.setDouble(2, city.getCityGeoPoint().getLongitude());
            preparedStatement.setDouble(3, city.getCityGeoPoint().getLatitude());
            preparedStatement.setString(4, city.getCountry());
            rowsAffected = preparedStatement.executeUpdate();
        }

        if (rowsAffected == 1) {
            LOGGER.debug("City: " + city.toString() + " inserted successfully in db!!!");
        } else {
            LOGGER.debug("City: " + city.toString() + " failed to be inserted in db! (Possibly already stored)");
        }

        return rowsAffected;
    }

    @Override
    public List<City> findByUserId(int id) throws SQLException {

        final List<City> cityList = new ArrayList<>();
        final String query = "SELECT ct." + Table.Column.CITY_ID + ","
            + "ct." + Table.Column.CITY_NAME + ","
            + "ct." + Table.Column.CITY_LONGITUDE + ","
            + "ct." + Table.Column.CITY_LATITUDE + ","
            + "ct." + Table.Column.CITY_COUNTRY
            + " FROM " + Table.CITY + " ct INNER JOIN " + SqliteUserCityDao.Table.USER_CITY + " uct "
            + "ON ct." + Table.Column.CITY_ID + "=" + "uct." + SqliteUserCityDao.Table.Column.CITY_ID
            + " WHERE uct." + SqliteUserCityDao.Table.Column.USER_ID + "=" + id;

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
        }

        return cityList;
    }

    @Override
    public Optional<City> findByCityName(String name) throws SQLException {

        final String query = "SELECT * FROM " + Table.CITY
            + " WHERE " + Table.Column.CITY_NAME + "=" + "'" + name + "'";

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
        }

        return Optional.ofNullable(city);
    }
}
