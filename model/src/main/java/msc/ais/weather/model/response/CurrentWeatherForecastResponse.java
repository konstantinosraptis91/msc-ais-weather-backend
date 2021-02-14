package msc.ais.weather.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import msc.ais.weather.model.CurrentWeatherForecast;
import msc.ais.weather.model.location.City;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 23/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public class CurrentWeatherForecastResponse {

    private final City city;
    private final CurrentWeatherForecast forecast;

    private CurrentWeatherForecastResponse(Builder builder) {
        this.city = builder.city;
        this.forecast = builder.forecast;
    }

    public City getCity() {
        return city;
    }

    @JsonProperty("current")
    public CurrentWeatherForecast getForecast() {
        return forecast;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private City city;
        private CurrentWeatherForecast forecast;

        public Builder city(City city) {
            this.city = city;
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
