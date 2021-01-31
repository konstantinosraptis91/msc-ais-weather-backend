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
import ms.ais.weather.model.utils.HourlyWeatherForecastResponseDeserializer;
import ms.ais.weather.service.enums.UnitsType;
import ms.ais.weather.service.tasks.GetFromOpenWeatherMapTask;
import ms.ais.weather.service.tasks.OpenWeatherMapURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 19/1/2021.
 */
public class OpenWeatherMapService implements WeatherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherMapService.class);

    @Override
    public CurrentWeatherForecastResponse getCurrentWeatherForecastResponse(String cityName) {

        GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.createWithURI(
            OpenWeatherMapURI.builder()
                .withCityName(cityName)
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
            // Insert city in db in case it is not already stored
            CityDao cityDao = DaoFactory.createCityDao();
            cityDao.insertCity(City.builder()
                .cityGeoPoint(response.getCityGeoPoint())
                .build());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (SQLException e) {
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
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
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
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return response;
    }

    private City findCityByNameOrThrowException(String name) {
        return ServiceFactory.createCityService()
            .findCityByName(name)
            .orElseThrow(() -> new NoSuchElementException(
                "Error... Cannot find city : " + name + " neither in db, nor in OpenWeatherMap API."));
    }
}
