package ms.ais.weather.model;

import ms.ais.weather.model.conditions.Conditions;
import ms.ais.weather.model.enums.WeatherForecastType;
import ms.ais.weather.model.location.CityGeoPoint;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public class CurrentWeatherForecast extends WeatherForecast {

    private CurrentWeatherForecast(Builder builder) {
        super(WeatherForecastType.CURRENT);
        this.conditionsList = builder.conditionsList;
        super.timestamp = builder.timestamp;
        super.cityGeoPoint = builder.cityGeoPoint;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends WeatherForecast.Builder {

        @Override
        public Builder addConditions(Conditions conditions) {
            super.addConditions(conditions);
            return this;
        }

        @Override
        public Builder withTimestamp(long timestamp) {
            super.withTimestamp(timestamp);
            return this;
        }

        @Override
        public Builder withCityGeoPoint(CityGeoPoint cityGeoPoint) {
            super.withCityGeoPoint(cityGeoPoint);
            return this;
        }

        @Override
        public CurrentWeatherForecast build() {
            return new CurrentWeatherForecast(this);
        }
    }

}
