package ms.ais.weather.model;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 10/1/2021.
 */
public class DailyWeatherForecast extends HourlyWeatherForecast {

    private double currentTemperature;
    private double maxTemperature;
    private double minTemperature;
    private boolean rainPossibility;
    private double humidityPercentage;
    private double windSpeed;
    private String windDirection;
    private long timestamp;

}
