package msc.ais.weather.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import msc.ais.weather.model.DailyWeatherForecast;
import msc.ais.weather.model.conditions.CurrentTemperatureConditions;
import msc.ais.weather.model.location.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 23/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public class DailyWeatherForecastResponse {

    private final City city;
    private final CurrentTemperatureConditions temperatureConditions;
    private List<DailyWeatherForecast> forecastList;

    private DailyWeatherForecastResponse(Builder builder) {
        this.city = builder.city;
        this.temperatureConditions = builder.temperatureConditions;
    }

    public City getCity() {
        return city;
    }

    @JsonProperty("currentTemperatureConditions")
    public CurrentTemperatureConditions getTemperatureConditions() {
        return temperatureConditions;
    }

    @JsonProperty("daily")
    public List<DailyWeatherForecast> getForecastList() {
        if (Objects.isNull(forecastList)) {
            forecastList = new ArrayList<>();
        }
        return forecastList;
    }

    public interface DailyWeatherForecastResponseCityGeoPoint {
        DailyWeatherForecastResponseCurrentTemperatureConditions city(City city);
    }

    public interface DailyWeatherForecastResponseCurrentTemperatureConditions {
        DailyWeatherForecastResponseBuild currentTemperatureConditions(CurrentTemperatureConditions temperatureConditions);
    }

    public interface DailyWeatherForecastResponseBuild {
        DailyWeatherForecastResponse build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements
        DailyWeatherForecastResponseCityGeoPoint,
        DailyWeatherForecastResponseCurrentTemperatureConditions,
        DailyWeatherForecastResponseBuild {

        private City city;
        private CurrentTemperatureConditions temperatureConditions;

        @Override
        public DailyWeatherForecastResponseCurrentTemperatureConditions city(City city) {
            this.city = city;
            return this;
        }

        @Override
        public DailyWeatherForecastResponseBuild currentTemperatureConditions(CurrentTemperatureConditions temperatureConditions) {
            this.temperatureConditions = temperatureConditions;
            return this;
        }

        @Override
        public DailyWeatherForecastResponse build() {
            return new DailyWeatherForecastResponse(this);
        }
    }
}
