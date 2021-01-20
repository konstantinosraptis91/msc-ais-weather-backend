package ms.ais.weather.retriever;

import ms.ais.weather.model.CurrentWeatherForecast;
import org.junit.jupiter.api.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 19/1/2021.
 */
public class OpenWeatherMapDataRetrieverTest {

    @Test
    public void testGetCurrentWeatherForecast() throws Exception {

        WeatherDataRetriever dataRetriever = new OpenWeatherMapDataRetriever();
        CurrentWeatherForecast weatherForecast = dataRetriever.getCurrentWeatherForecast();

        System.out.println("Type: " + weatherForecast.getType());
        System.out.println("Timestamp: " + weatherForecast.getTimestamp());
        System.out.println(weatherForecast.getCityGeoPoint());

        weatherForecast
            .getConditionsList()
            .forEach(System.out::println);
    }

}
