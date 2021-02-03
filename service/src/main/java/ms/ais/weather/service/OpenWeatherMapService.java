package ms.ais.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ms.ais.weather.db.AliasDao;
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
import ms.ais.weather.service.cache.OpenWeatherMapCache;
import ms.ais.weather.service.enums.UnitsType;
import ms.ais.weather.service.tasks.GetFromOpenWeatherMapTask;
import ms.ais.weather.service.tasks.OpenWeatherMapURI;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 19/1/2021.
 */
public class OpenWeatherMapService implements WeatherService, GeocodingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherMapService.class);

    /**
     * Get current weather forecast, which contains:
     * -current:
     * --temperature conditions, weather conditions, wind conditions
     *
     * @param cityName The city name
     * @return The weather forecast
     */
    @Override
    public Optional<CurrentWeatherForecastResponse> getCurrentWeatherForecastResponse(String cityName) {

        CurrentWeatherForecastResponse response = null;

        try {
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


            String jsonString = getJsonStringFromCacheOrPerformAPICall(task);
            response = mapper.readValue(jsonString, CurrentWeatherForecastResponse.class);

        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return Optional.ofNullable(response);
    }

    /**
     * Get hourly weather forecast for the next 2 days, which contains:
     * -hourly:
     * --temperature conditions, weather conditions
     *
     * @param cityName The city name
     * @return The weather forecast
     */
    @Override
    public Optional<HourlyWeatherForecastResponse> getHourlyWeatherForecastResponse(String cityName) {

        HourlyWeatherForecastResponse response = null;

        try {
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

            String jsonString = getJsonStringFromCacheOrPerformAPICall(task);
            response = mapper.readValue(jsonString, HourlyWeatherForecastResponse.class);
        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return Optional.ofNullable(response);
    }

    /**
     * Get daily weather forecast for the next 5 days, which contains:
     * -current:
     * --temperature conditions
     * -daily:
     * --temperature conditions, weather conditions, wind conditions
     *
     * @param cityName The city name
     * @return The weather forecast
     */
    @Override
    public Optional<DailyWeatherForecastResponse> getDailyWeatherForecastResponse(String cityName) {

        DailyWeatherForecastResponse response = null;

        try {
            City city = findCityByNameOrThrowException(cityName);

            GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.createWithURI(
                OpenWeatherMapURI.builder()
                    .longitude(city.getCityGeoPoint().getLongitude())
                    .latitude(city.getCityGeoPoint().getLatitude())
                    .withKey("200681ee8b9be15aafc017130d88cd41")
                    .withUnitsType(UnitsType.METRIC)
                    .withWeatherForecastType(WeatherForecastType.DAILY)
                    .build().getURI());

            final ObjectMapper mapper = new ObjectMapper();
            final SimpleModule module = new SimpleModule();

            CurrentWeatherForecastResponse currentWeatherForecastResponse
                = getCurrentWeatherForecastResponse(cityName)
                .orElseThrow(() -> new NoSuchElementException(
                    "Cannot get a current weather forecast response..."));

            module.addDeserializer(DailyWeatherForecastResponse.class,
                new DailyWeatherForecastResponseDeserializer(
                    currentWeatherForecastResponse
                        .getForecast()
                        .getTemperatureConditions()));
            mapper.registerModule(module);

            String jsonString = getJsonStringFromCacheOrPerformAPICall(task);
            response = mapper.readValue(jsonString, DailyWeatherForecastResponse.class);
        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return Optional.ofNullable(response);
    }

    /**
     * Get city by name or alias from openWeatherMap API.
     *
     * @param nameOrAlias The city name
     * @return The city
     */
    @Override
    public Optional<City> getCityByName(String nameOrAlias) {

        City city = null;

        try {
            GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.createWithURI(
                new URIBuilder()
                    .setScheme("https")
                    .setHost("api.openweathermap.org/geo/1.0")
                    .setPath("/direct")
                    .setParameter("q", nameOrAlias)
                    .setParameter("appid", "200681ee8b9be15aafc017130d88cd41")
                    .setParameter("limit", "1").build());

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(City.class, new GeocodingCityDeserializer());
            mapper.registerModule(module);

            city = mapper.readValue(task.call(), City.class);

            CityDao cityDao = DaoFactory.createCityDao();
            int generatedKey = cityDao.insertCity(city);
            int cityId;

            if (generatedKey != -1) {
                cityId = generatedKey;
            } else {
                cityId = cityDao.findCityIdByNameOrAlias(city.getCityGeoPoint().getCityName())
                    .orElseThrow(() -> new NoSuchElementException(
                        "Cannot find city: " + nameOrAlias + " in db (city_name or alias_name)."));
            }

            // If city name taken from API not equal with given name, create an alias
            if (!city.getCityGeoPoint().getCityName().equals(nameOrAlias)) {
                LOGGER.debug("Adding an alias for " + city.getCityGeoPoint().getCityName() + " -> " + nameOrAlias);
                AliasDao aliasDao = DaoFactory.createAliasDao();
                aliasDao.insertAlias(cityId, nameOrAlias);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return Optional.ofNullable(city);
    }


    private String getJsonStringFromCacheOrPerformAPICall(GetFromOpenWeatherMapTask task)
        throws IOException, InterruptedException {

        String jsonString;

        if (OpenWeatherMapCache.INSTANCE.getCache().containsKey(task.getURI().toString())) {
            LOGGER.debug("Call: [" + task.getURI().toString() + "] found and serving from cache!!!");
            jsonString = OpenWeatherMapCache.INSTANCE.getCache().get(task.getURI().toString());
        } else {
            LOGGER.debug("Call: [" + task.getURI().toString()
                + "] not found in cache, performing a call to openWeatherMap API.");
            jsonString = task.call();
            LOGGER.debug("Storing... Call: [" + task.getURI().toString() + "]result in cache.");
            OpenWeatherMapCache.INSTANCE.getCache().put(task.getURI().toString(), jsonString);
            LOGGER.debug("Call: [" + task.getURI().toString() + "] stored successfully in cache!!!");
        }

        return jsonString;
    }

    /**
     * Try to find city in db or get it from openWeatherMap API.
     *
     * @param name The city name
     * @return The city
     */
    private City findCityByNameOrThrowException(String name) {
        return ServiceFactory.createCityService()
            .findCityByName(name)
            .orElseThrow(() -> new NoSuchElementException(
                "Error... Cannot find city : " + name + " neither in db, nor in OpenWeatherMap API."));
    }
}
