package ms.ais.weather.service;

import ms.ais.weather.model.response.CurrentWeatherForecastResponse;
import ms.ais.weather.model.response.DailyWeatherForecastResponse;
import ms.ais.weather.model.response.HourlyWeatherForecastResponse;

import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 19/1/2021.
 */
public interface WeatherService {

    Optional<CurrentWeatherForecastResponse> getCurrentWeatherForecastResponse(String cityName);

    Optional<HourlyWeatherForecastResponse> getHourlyWeatherForecastResponse(String cityName);

    Optional<DailyWeatherForecastResponse> getDailyWeatherForecastResponse(String cityName);

    Optional<CurrentWeatherForecastResponse> getCurrentWeatherForecastResponse();

    Optional<HourlyWeatherForecastResponse> getHourlyWeatherForecastResponse();

    Optional<DailyWeatherForecastResponse> getDailyWeatherForecastResponse();
}
