package ms.ais.weather.service;

import ms.ais.weather.model.response.HourlyWeatherForecastResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 19/1/2021.
 */
public class OpenWeatherMapServiceTest {

    @Disabled
    @Test
    public void testGetCurrentWeatherForecast() throws Exception {

//        WeatherDataRetriever dataRetriever = new OpenWeatherMapDataRetriever();
//        CurrentWeatherForecast weatherForecast = dataRetriever.getCurrentWeatherForecast();
//
//        System.out.println("Type: " + weatherForecast.getType());
//        System.out.println("Timestamp: " + weatherForecast.getTimestamp());
//        System.out.println(weatherForecast.getCityGeoPoint());
//
//        weatherForecast
//            .getConditionsList()
//            .forEach(System.out::println);
    }

    @Disabled
    @Test
    public void testGetHourlyWeatherForecastResponse() throws Exception {

        WeatherService dataRetriever = new OpenWeatherMapService();
        HourlyWeatherForecastResponse response = dataRetriever.getHourlyWeatherForecastResponse("Paris");

        System.out.println(response.getCityGeoPoint());

        response
            .getForecastList()
            .forEach(System.out::println);
    }

}
