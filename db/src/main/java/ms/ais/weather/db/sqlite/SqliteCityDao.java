package ms.ais.weather.db.sqlite;

import ms.ais.weather.db.CityDao;
import ms.ais.weather.model.db.City;
import ms.ais.weather.model.location.CityGeoPoint;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class SqliteCityDao implements CityDao {

    public enum Table {
        CITY;

        public enum Column {
            CITY_ID, CITY_NAME, CITY_LONGITUDE, CITY_LATITUDE, CITY_COUNTRY
        }
    }

    @Override
    public int insertCity(City city) throws SQLException {

        final String query = "INSERT INTO " + Table.CITY
            + " ("
            + Table.Column.CITY_NAME + ","
            + Table.Column.CITY_LONGITUDE + ","
            + Table.Column.CITY_LATITUDE + ","
            + Table.Column.CITY_COUNTRY
            + ")"
            + " VALUES (?, ?, ?, ?)";

        int rowsAffected;

        try (PreparedStatement preparedStatement = DBCPDataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, city.getCityGeoPoint().getCityName());
            preparedStatement.setDouble(2, city.getCityGeoPoint().getLongitude());
            preparedStatement.setDouble(3, city.getCityGeoPoint().getLatitude());
            preparedStatement.setString(4, city.getCountry());
            rowsAffected = preparedStatement.executeUpdate();
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

        Statement statement = DBCPDataSource.getConnection().createStatement();
        try (ResultSet resultSet = statement.executeQuery(query)) {

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
        Statement statement = DBCPDataSource.getConnection().createStatement();
        try (ResultSet resultSet = statement.executeQuery(query)) {

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
