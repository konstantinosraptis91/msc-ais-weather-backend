package ms.ais.weather.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ms.ais.weather.model.DailyWeatherForecast;
import ms.ais.weather.model.conditions.CurrentTemperatureConditions;
import ms.ais.weather.model.location.CityGeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 23/1/2021.
 */
public class DailyWeatherForecastResponse {

    private final CityGeoPoint cityGeoPoint;
    private final CurrentTemperatureConditions temperatureConditions;
    private List<DailyWeatherForecast> forecastList;

    private DailyWeatherForecastResponse(Builder builder) {
        this.cityGeoPoint = builder.cityGeoPoint;
        this.temperatureConditions = builder.temperatureConditions;
    }

    public CityGeoPoint getCityGeoPoint() {
        return cityGeoPoint;
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
        DailyWeatherForecastResponseCurrentTemperatureConditions cityGeoPoint(CityGeoPoint cityGeoPoint);
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

        private CityGeoPoint cityGeoPoint;
        private CurrentTemperatureConditions temperatureConditions;

        @Override
        public DailyWeatherForecastResponseCurrentTemperatureConditions cityGeoPoint(CityGeoPoint cityGeoPoint) {
            this.cityGeoPoint = cityGeoPoint;
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
