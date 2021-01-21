package ms.ais.weather.model;

import ms.ais.weather.model.enums.WeatherForecastType;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 10/1/2021.
 */
public class DailyWeatherForecast extends WeatherForecast {

    private double currentTemperature;
    private double maxTemperature;
    private double minTemperature;
    private boolean rainPossibility;
    private double humidityPercentage;
    private double windSpeed;
    private String windDirection;

    protected DailyWeatherForecast(Builder builder) {
        super(WeatherForecastType.DAILY);
    }

    public static class Builder {



    }

}
