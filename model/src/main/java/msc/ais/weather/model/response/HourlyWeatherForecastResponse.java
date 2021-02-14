package msc.ais.weather.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import msc.ais.weather.model.HourlyWeatherForecast;
import msc.ais.weather.model.location.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 22/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public class HourlyWeatherForecastResponse {

    private final City city;
    private List<HourlyWeatherForecast> forecastList;

    private HourlyWeatherForecastResponse(City city) {
        this.city = city;
    }

    public static HourlyWeatherForecastResponse createInstance(City city) {
        return new HourlyWeatherForecastResponse(city);
    }

    public City getCity() {
        return city;
    }

    @JsonProperty("hourly")
    public List<HourlyWeatherForecast> getForecastList() {
        if (Objects.isNull(forecastList)) {
            forecastList = new ArrayList<>();
        }
        return forecastList;
    }
}
