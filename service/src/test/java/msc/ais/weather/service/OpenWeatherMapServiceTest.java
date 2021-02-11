package msc.ais.weather.service;

import msc.ais.weather.model.location.City;
import msc.ais.weather.model.response.CurrentWeatherForecastResponse;
import msc.ais.weather.model.response.DailyWeatherForecastResponse;
import msc.ais.weather.model.response.HourlyWeatherForecastResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 19/1/2021.
 */
public class OpenWeatherMapServiceTest {

    private final ServiceOptions OPTIONS = ServiceOptions.builder()
        .openWeatherMapAPIKey("200681ee8b9be15aafc017130d88cd41")
        .ipStackAPIKey("8f0513df0cc0f66506cad2a187e485d6")
        .build();

    @Disabled
    @Test
    public void testGetCurrentWeatherForecastForCurrentLocation() throws Exception {

        WeatherService service = ServiceFactory.createOpenWeatherMapWeatherService(OPTIONS);
        CurrentWeatherForecastResponse response =
            service.getCurrentWeatherForecastResponse()
                .orElseThrow();

        Assertions.assertNotNull(response.getCity());
        System.out.println(response.getCity());

        Assertions.assertNotNull(response.getForecast());
        System.out.println(response.getForecast());
    }

    @Disabled
    @Test
    public void testGetCurrentWeatherForecast() throws Exception {

        WeatherService service = ServiceFactory.createOpenWeatherMapWeatherService(OPTIONS);
        CurrentWeatherForecastResponse response =
            service.getCurrentWeatherForecastResponse("Paris")
                .orElseThrow();

        Assertions.assertNotNull(response.getCity());
        System.out.println(response.getCity());

        Assertions.assertNotNull(response.getForecast());
        System.out.println(response.getForecast());
    }

    @Disabled
    @Test
    public void testGetCurrentWeatherForecastByIP() throws Exception {

        WeatherService service = ServiceFactory.createOpenWeatherMapWeatherService(OPTIONS);
        CurrentWeatherForecastResponse response =
            service.getCurrentWeatherForecastResponseByIP("94.65.15.200")
                .orElseThrow();

        Assertions.assertNotNull(response.getCity());
        System.out.println(response.getCity());

        Assertions.assertNotNull(response.getForecast());
        System.out.println(response.getForecast());
    }

    @Disabled
    @Test
    public void testGetHourlyWeatherForecastResponseForCurrentLocation() throws Exception {

        WeatherService service = ServiceFactory.createOpenWeatherMapWeatherService(OPTIONS);
        HourlyWeatherForecastResponse response =
            service.getHourlyWeatherForecastResponse()
                .orElseThrow();

        Assertions.assertNotNull(response.getCity());
        System.out.println(response.getCity());

        Assertions.assertFalse(response.getForecastList().isEmpty());
        response
            .getForecastList()
            .forEach(System.out::println);
    }

    @Disabled
    @Test
    public void testGetHourlyWeatherForecastResponse() throws Exception {

        WeatherService service = ServiceFactory.createOpenWeatherMapWeatherService(OPTIONS);
        HourlyWeatherForecastResponse response =
            service.getHourlyWeatherForecastResponse("Paris")
                .orElseThrow();

        Assertions.assertNotNull(response.getCity());
        System.out.println(response.getCity());

        Assertions.assertFalse(response.getForecastList().isEmpty());
        response
            .getForecastList()
            .forEach(System.out::println);
    }

    @Disabled
    @Test
    public void testGetHourlyWeatherForecastResponseByIP() throws Exception {

        WeatherService service = ServiceFactory.createOpenWeatherMapWeatherService(OPTIONS);
        HourlyWeatherForecastResponse response =
            service.getHourlyWeatherForecastResponseByIP("94.65.15.200")
                .orElseThrow();

        Assertions.assertNotNull(response.getCity());
        System.out.println(response.getCity());

        Assertions.assertFalse(response.getForecastList().isEmpty());
        response
            .getForecastList()
            .forEach(System.out::println);
    }

    @Disabled
    @Test
    public void testGetDailyWeatherForecastResponseForCurrentLocation() throws Exception {

        WeatherService service = ServiceFactory.createOpenWeatherMapWeatherService(OPTIONS);
        DailyWeatherForecastResponse response =
            service.getDailyWeatherForecastResponse()
                .orElseThrow();

        Assertions.assertNotNull(response.getCity());
        System.out.println(response.getCity());

        Assertions.assertFalse(response.getForecastList().isEmpty());
        response
            .getForecastList()
            .forEach(System.out::println);

    }

    @Disabled
    @Test
    public void testGetDailyWeatherForecastResponse() throws Exception {

        WeatherService service = ServiceFactory.createOpenWeatherMapWeatherService(OPTIONS);
        DailyWeatherForecastResponse response =
            service.getDailyWeatherForecastResponse("Paris")
                .orElseThrow();

        Assertions.assertNotNull(response.getCity());
        System.out.println(response.getCity());

        Assertions.assertFalse(response.getForecastList().isEmpty());
        response
            .getForecastList()
            .forEach(System.out::println);

    }

    // @Disabled
    @Test
    public void testGetDailyWeatherForecastResponseByIP() throws Exception {

        WeatherService service = ServiceFactory.createOpenWeatherMapWeatherService(OPTIONS);
        DailyWeatherForecastResponse response =
            service.getDailyWeatherForecastResponseByIP("94.65.15.200")
                .orElseThrow();

        Assertions.assertNotNull(response.getCity());
        System.out.println(response.getCity());

        Assertions.assertFalse(response.getForecastList().isEmpty());
        response
            .getForecastList()
            .forEach(System.out::println);

    }

    @Disabled
    @Test
    public void testGetCityByName() {

        final String cityName = "Pallini";

        GeocodingService service = ServiceFactory.createOpenWeatherMapGeocodingService(OPTIONS);
        City city = service.getCityByName(cityName)
            .orElseThrow(() -> new NoSuchElementException(
                "Error from test... Cannot find city with name: " + cityName));

        Assertions.assertNotNull(city);
        System.out.println(city);
    }

    @Disabled
    @Test
    public void testGetCityByCurrentLocation() {

        GeocodingService service = ServiceFactory.createOpenWeatherMapGeocodingService(OPTIONS);
        City city = service.getCityByCurrentLocation()
            .orElseThrow(() -> new NoSuchElementException(
                "Error from test... Cannot find city based on current location."));

        Assertions.assertNotNull(city);
        System.out.println(city);
    }

    @Disabled
    @Test
    public void testCombineGeoServices() {

        GeocodingService service = ServiceFactory.createOpenWeatherMapGeocodingService(OPTIONS);
        City city1 = service.getCityByCurrentLocation()
            .orElseThrow(() -> new NoSuchElementException(
                "Error from test... Cannot find city based on current location."));

        Assertions.assertNotNull(city1);
        Assertions.assertNotNull(city1.getCityGeoPoint());
        Assertions.assertNotNull(city1.getCityGeoPoint().getCityName());
        String cityName1 = city1.getCityGeoPoint().getCityName();

        City city2 = service.getCityByName(cityName1)
            .orElseThrow(() -> new NoSuchElementException(
                "Error from test... Cannot find city with name: " + cityName1));

        Assertions.assertNotNull(city2);
        Assertions.assertNotNull(city2.getCityGeoPoint());
        Assertions.assertNotNull(city2.getCityGeoPoint().getCityName());
        System.out.println(city2);
    }

}
