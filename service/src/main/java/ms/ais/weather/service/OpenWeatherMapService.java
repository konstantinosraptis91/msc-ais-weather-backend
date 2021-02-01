package ms.ais.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ms.ais.weather.db.CityDao;
import ms.ais.weather.db.DaoFactory;
import ms.ais.weather.model.db.City;
import ms.ais.weather.model.enums.WeatherForecastType;
import ms.ais.weather.model.response.CurrentWeatherForecastResponse;
import ms.ais.weather.model.response.DailyWeatherForecastResponse;
import ms.ais.weather.model.response.HourlyWeatherForecastResponse;
import ms.ais.weather.model.utils.CurrentWeatherForecastResponseDeserializer;
import ms.ais.weather.model.utils.DailyWeatherForecastResponseDeserializer;
import ms.ais.weather.model.utils.GeocodingCityDeserializer;
import ms.ais.weather.model.utils.HourlyWeatherForecastResponseDeserializer;
import ms.ais.weather.service.enums.UnitsType;
import ms.ais.weather.service.tasks.GetFromOpenWeatherMapTask;
import ms.ais.weather.service.tasks.OpenWeatherMapURI;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 19/1/2021.
 */
public class OpenWeatherMapService implements WeatherService, GeocodingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherMapService.class);

    @Override
    public CurrentWeatherForecastResponse getCurrentWeatherForecastResponse(String cityName) {

        City city = findCityByNameOrThrowException(cityName);

        GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.createWithURI(
            OpenWeatherMapURI.builder()
                .withCityName(city.getCityGeoPoint().getCityName())
                .withKey("200681ee8b9be15aafc017130d88cd41")
                .withUnitsType(UnitsType.METRIC)
                .withWeatherForecastType(WeatherForecastType.CURRENT)
                .build().getURI());

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CurrentWeatherForecastResponse.class, new CurrentWeatherForecastResponseDeserializer());
        mapper.registerModule(module);

        CurrentWeatherForecastResponse response = null;

        try {
            response = mapper.readValue(task.call(), CurrentWeatherForecastResponse.class);

        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return response;
    }

    @Override
    public HourlyWeatherForecastResponse getHourlyWeatherForecastResponse(String cityName) {

        City city = findCityByNameOrThrowException(cityName);

        GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.createWithURI(
            OpenWeatherMapURI.builder()
                .longitude(city.getCityGeoPoint().getLongitude())
                .latitude(city.getCityGeoPoint().getLatitude())
                .withKey("200681ee8b9be15aafc017130d88cd41")
                .withUnitsType(UnitsType.METRIC)
                .withWeatherForecastType(WeatherForecastType.HOURLY)
                .build().getURI());

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(HourlyWeatherForecastResponse.class, new HourlyWeatherForecastResponseDeserializer());
        mapper.registerModule(module);

        HourlyWeatherForecastResponse response = null;

        try {
            response = mapper.readValue(task.call(), HourlyWeatherForecastResponse.class);
        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return response;
    }

    @Override
    public DailyWeatherForecastResponse getDailyWeatherForecastResponse(String cityName) {

        City city = findCityByNameOrThrowException(cityName);

        GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.createWithURI(
            OpenWeatherMapURI.builder()
                .longitude(city.getCityGeoPoint().getLongitude())
                .latitude(city.getCityGeoPoint().getLatitude())
                .withKey("200681ee8b9be15aafc017130d88cd41")
                .withUnitsType(UnitsType.METRIC)
                .withWeatherForecastType(WeatherForecastType.DAILY)
                .build().getURI());

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        CurrentWeatherForecastResponse currentWeatherForecastResponse = getCurrentWeatherForecastResponse(cityName);
        module.addDeserializer(DailyWeatherForecastResponse.class,
            new DailyWeatherForecastResponseDeserializer(
                currentWeatherForecastResponse
                    .getForecast()
                    .getTemperatureConditions()));
        mapper.registerModule(module);

        DailyWeatherForecastResponse response = null;

        try {
            response = mapper.readValue(task.call(), DailyWeatherForecastResponse.class);
        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return response;
    }

    @Override
    public Optional<City> getCityByName(String name) {

        City city = null;

        try {
            GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.createWithURI(
                new URIBuilder()
                    .setScheme("https")
                    .setHost("api.openweathermap.org/geo/1.0")
                    .setPath("/direct")
                    .setParameter("q", name)
                    .setParameter("appid", "200681ee8b9be15aafc017130d88cd41")
                    .setParameter("limit", "1").build());

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(City.class, new GeocodingCityDeserializer());
            mapper.registerModule(module);

            city = mapper.readValue(task.call(), City.class);

            CityDao cityDao = DaoFactory.createCityDao();
            cityDao.insertCity(city);

        } catch (URISyntaxException | IOException | InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return Optional.ofNullable(city);
    }

    private City findCityByNameOrThrowException(String name) {
        return ServiceFactory.createCityService()
            .findCityByName(name)
            .orElseThrow(() -> new NoSuchElementException(
                "Error... Cannot find city : " + name + " neither in db, nor in OpenWeatherMap API."));
    }
}
