package msc.ais.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import msc.ais.weather.model.location.City;
import msc.ais.weather.model.enums.WeatherForecastType;
import msc.ais.weather.model.response.CurrentWeatherForecastResponse;
import msc.ais.weather.model.response.DailyWeatherForecastResponse;
import msc.ais.weather.model.response.HourlyWeatherForecastResponse;
import msc.ais.weather.model.utils.*;
import msc.ais.weather.service.cache.ServiceCache;
import msc.ais.weather.service.enums.UnitsType;
import msc.ais.weather.service.tasks.GetFromOpenWeatherMapTask;
import msc.ais.weather.service.tasks.OpenWeatherMapURI;
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
     * Get current weather forecast for the current location, which contains:
     * -current:
     * --temperature conditions, weather conditions, wind conditions
     *
     * @return The weather forecast
     */
    @Override
    public Optional<CurrentWeatherForecastResponse> getCurrentWeatherForecastResponse() {

        Optional<CurrentWeatherForecastResponse> oResponse = Optional.empty();

        try {
            City city = getCityByCurrentLocation()
                .orElseThrow(() -> new NoSuchElementException(
                    "Error... Cannot find city for the current location."));

            oResponse = getCurrentWeatherForecastResponse(city.getCityGeoPoint().getCityName());

        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return oResponse;
    }

    /**
     * Get current weather forecast for the location mapped to the given IP address, which contains:
     * -current:
     * --temperature conditions, weather conditions, wind conditions
     *
     * @return The weather forecast
     */
    @Override
    public Optional<CurrentWeatherForecastResponse> getCurrentWeatherForecastResponseByIP(String ip) {

        Optional<CurrentWeatherForecastResponse> oResponse = Optional.empty();

        try {
            City city = getCityByIP(ip)
                .orElseThrow(() -> new NoSuchElementException(
                    "Error... Cannot find city for the given ip: " + ip));

            oResponse = getCurrentWeatherForecastResponse(city.getCityGeoPoint().getCityName());

        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return oResponse;
    }

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
            City city = getCityByName(cityName)
                .orElseThrow(() -> new NoSuchElementException(
                    "Error... Cannot find city : " + cityName
                        + " neither in cache, nor in OpenWeatherMap API."));

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
            // finalize city
//            response.getCity().setId(city.getId());
//            response.getCity().setCountry(city.getCountry());
//            response.getCity().getCityGeoPoint().setCityName(city.getCityGeoPoint().getCityName());

        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return Optional.ofNullable(response);
    }

    /**
     * Get hourly weather forecast for the next 2 days for the current location, which contains:
     * -hourly:
     * --temperature conditions, weather conditions
     *
     * @return The weather forecast
     */
    @Override
    public Optional<HourlyWeatherForecastResponse> getHourlyWeatherForecastResponse() {

        Optional<HourlyWeatherForecastResponse> oResponse = Optional.empty();

        try {
            City city = getCityByCurrentLocation()
                .orElseThrow(() -> new NoSuchElementException(
                    "Error... Cannot find city for the current location."));

            oResponse = getHourlyWeatherForecastResponse(city.getCityGeoPoint().getCityName());

        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return oResponse;
    }

    /**
     * Get hourly weather forecast for the next 2 days for the location mapped to the given IP address, which contains:
     * -hourly:
     * --temperature conditions, weather conditions
     *
     * @return The weather forecast
     */
    @Override
    public Optional<HourlyWeatherForecastResponse> getHourlyWeatherForecastResponseByIP(String ip) {

        Optional<HourlyWeatherForecastResponse> oResponse = Optional.empty();

        try {
            City city = getCityByIP(ip)
                .orElseThrow(() -> new NoSuchElementException(
                    "Error... Cannot find city for the given ip: " + ip));

            oResponse = getHourlyWeatherForecastResponse(city.getCityGeoPoint().getCityName());

        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return oResponse;
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
            City city = getCityByName(cityName)
                .orElseThrow(() -> new NoSuchElementException(
                    "Error... Cannot find city : " + cityName
                        + " neither in cache, nor in OpenWeatherMap API."));

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
            // finalize city
//            response.getCity().setId(city.getId());
//            response.getCity().setCountry(city.getCountry());
//            response.getCity().getCityGeoPoint().setCityName(city.getCityGeoPoint().getCityName());

        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return Optional.ofNullable(response);
    }

    /**
     * Get daily weather forecast for the next 5 days for the current location, which contains:
     * -current:
     * --temperature conditions
     * -daily:
     * --temperature conditions, weather conditions, wind conditions
     *
     * @return The weather forecast
     */
    @Override
    public Optional<DailyWeatherForecastResponse> getDailyWeatherForecastResponse() {

        Optional<DailyWeatherForecastResponse> oResponse = Optional.empty();

        try {
            City city = getCityByCurrentLocation()
                .orElseThrow(() -> new NoSuchElementException(
                    "Error... Cannot find city for the current location."));

            oResponse = getDailyWeatherForecastResponse(city.getCityGeoPoint().getCityName());

        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return oResponse;
    }

    /**
     * Get daily weather forecast for the next 5 days for the location mapped to given IP address, which contains:
     * -current:
     * --temperature conditions
     * -daily:
     * --temperature conditions, weather conditions, wind conditions
     *
     * @return The weather forecast
     */
    @Override
    public Optional<DailyWeatherForecastResponse> getDailyWeatherForecastResponseByIP(String ip) {

        Optional<DailyWeatherForecastResponse> oResponse = Optional.empty();

        try {
            City city = getCityByIP(ip)
                .orElseThrow(() -> new NoSuchElementException(
                    "Error... Cannot find city for the given ip: " + ip));

            oResponse = getDailyWeatherForecastResponse(city.getCityGeoPoint().getCityName());

        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return oResponse;
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
            City city = getCityByName(cityName)
                .orElseThrow(() -> new NoSuchElementException(
                    "Error... Cannot find city : " + cityName
                        + " neither in cache, nor in OpenWeatherMap API."));

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
            // finalize city
//            response.getCity().setId(city.getId());
//            response.getCity().setCountry(city.getCountry());
//            response.getCity().getCityGeoPoint().setCityName(city.getCityGeoPoint().getCityName());

        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }

        return Optional.ofNullable(response);
    }

    /**
     * Get city by name from openWeatherMap API.
     *
     * @param cityName The city name
     * @return The city
     */
    @Override
    public Optional<City> getCityByName(String cityName) {

        City city = null;

        try {
            GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.createWithURI(
                new URIBuilder()
                    .setScheme("https")
                    .setHost("api.openweathermap.org/geo/1.0")
                    .setPath("/direct")
                    .setParameter("q", cityName)
                    .setParameter("appid", "200681ee8b9be15aafc017130d88cd41")
                    .setParameter("limit", "1").build());

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(City.class, new GeocodingCityDeserializer());
            mapper.registerModule(module);

            String jsonString = getJsonStringFromCacheOrPerformAPICall(task);
            city = mapper.readValue(jsonString, City.class);


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

        if (ServiceCache.OPEN_WEATHER_MAP_CACHE.getCache().containsKey(task.getURI().toString())) {
            LOGGER.debug("Call: [" + task.getURI().toString() + "] found and serving from cache!!!");
            jsonString = ServiceCache.OPEN_WEATHER_MAP_CACHE.getCache().get(task.getURI().toString());
        } else {
            LOGGER.debug("Call: [" + task.getURI().toString()
                + "] not found in cache, performing a call to openWeatherMap API.");
            jsonString = task.call();
            LOGGER.debug("Storing... Call: [" + task.getURI().toString() + "] in cache.");
            ServiceCache.OPEN_WEATHER_MAP_CACHE.getCache().put(task.getURI().toString(), jsonString);
            LOGGER.debug("Call: [" + task.getURI().toString() + "] stored successfully in cache!!!");
        }

        return jsonString;
    }

    /**
     * Try to find city for the current location by using ipstack API.
     *
     * @return The city
     */
    @Override
    public Optional<City> getCityByCurrentLocation() {

        City city = null;

        try {

            GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.createWithURI(
                new URIBuilder()
                    .setScheme("http")
                    .setHost("api.ipstack.com")
                    .setPath("/check")
                    .setParameter("access_key", "8f0513df0cc0f66506cad2a187e485d6")
                    .build());

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(City.class, new IPStackCityDeserializer());
            mapper.registerModule(module);

            city = mapper.readValue(task.call(), City.class);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return Optional.ofNullable(city);
    }

    /**
     * Try to find city for the location mapped to given IP address, by using ipstack API.
     *
     * @return The city
     */
    @Override
    public Optional<City> getCityByIP(String ip) {

        City city = null;

        try {

            GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.createWithURI(
                new URIBuilder()
                    .setScheme("http")
                    .setHost("api.ipstack.com")
                    .setPath("/" + ip)
                    .setParameter("access_key", "8f0513df0cc0f66506cad2a187e485d6")
                    .build());

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(City.class, new IPStackCityDeserializer());
            mapper.registerModule(module);

            city = mapper.readValue(task.call(), City.class);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return Optional.ofNullable(city);
    }
}
