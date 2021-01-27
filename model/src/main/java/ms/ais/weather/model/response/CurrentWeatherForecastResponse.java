package ms.ais.weather.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ms.ais.weather.model.CurrentWeatherForecast;
import ms.ais.weather.model.location.CityGeoPoint;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 23/1/2021.
 */
public class CurrentWeatherForecastResponse {

    private final CityGeoPoint cityGeoPoint;
    private final CurrentWeatherForecast forecast;

    private CurrentWeatherForecastResponse(Builder builder) {
        this.cityGeoPoint = builder.cityGeoPoint;
        this.forecast = builder.forecast;
    }

    public CityGeoPoint getCityGeoPoint() {
        return cityGeoPoint;
    }

    @JsonProperty("current")
    public CurrentWeatherForecast getForecast() {
        return forecast;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private CityGeoPoint cityGeoPoint;
        private CurrentWeatherForecast forecast;

        public Builder cityGeoPoint(CityGeoPoint cityGeoPoint) {
            this.cityGeoPoint = cityGeoPoint;
            return this;
        }

        public Builder currentWeatherForecast(CurrentWeatherForecast forecast) {
            this.forecast = forecast;
            return this;
        }

        public CurrentWeatherForecastResponse build() {
            return new CurrentWeatherForecastResponse(this);
        }

    }

}
