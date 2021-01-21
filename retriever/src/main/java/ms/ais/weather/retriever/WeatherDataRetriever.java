package ms.ais.weather.retriever;

import ms.ais.weather.model.CurrentWeatherForecast;
import ms.ais.weather.model.DailyWeatherForecast;
import ms.ais.weather.model.HourlyWeatherForecast;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 19/1/2021.
 */
public interface WeatherDataRetriever {

    CurrentWeatherForecast getCurrentWeatherForecast(String cityName);

    CurrentWeatherForecast getCurrentWeatherForecast();

    HourlyWeatherForecast getHourlyWeatherForecast();

    DailyWeatherForecast getDailyWeatherForecast();

}
