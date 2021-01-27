package ms.ais.weather.retriever;

import ms.ais.weather.model.response.CurrentWeatherForecastResponse;
import ms.ais.weather.model.response.DailyWeatherForecastResponse;
import ms.ais.weather.model.response.HourlyWeatherForecastResponse;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 19/1/2021.
 */
public interface WeatherDataRetriever {

    CurrentWeatherForecastResponse getCurrentWeatherForecastResponse(String cityName);

    HourlyWeatherForecastResponse getHourlyWeatherForecastResponse(String cityName);

    DailyWeatherForecastResponse getDailyWeatherForecastResponse(String cityName);
}
