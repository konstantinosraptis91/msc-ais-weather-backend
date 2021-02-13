package msc.ais.weather.service;

import msc.ais.weather.model.response.CurrentWeatherForecastResponse;
import msc.ais.weather.model.response.DailyWeatherForecastResponse;
import msc.ais.weather.model.response.HourlyWeatherForecastResponse;

import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 19/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public interface WeatherService {

    Optional<CurrentWeatherForecastResponse> getCurrentWeatherForecastResponse(String cityName);

    Optional<HourlyWeatherForecastResponse> getHourlyWeatherForecastResponse(String cityName);

    Optional<DailyWeatherForecastResponse> getDailyWeatherForecastResponse(String cityName);

    Optional<CurrentWeatherForecastResponse> getCurrentWeatherForecastResponseByIP(String ip);

    Optional<HourlyWeatherForecastResponse> getHourlyWeatherForecastResponseByIP(String ip);

    Optional<DailyWeatherForecastResponse> getDailyWeatherForecastResponseByIP(String ip);

    Optional<CurrentWeatherForecastResponse> getCurrentWeatherForecastResponse();

    Optional<HourlyWeatherForecastResponse> getHourlyWeatherForecastResponse();

    Optional<DailyWeatherForecastResponse> getDailyWeatherForecastResponse();
}
