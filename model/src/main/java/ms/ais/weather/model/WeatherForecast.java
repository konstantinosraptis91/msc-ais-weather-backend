package ms.ais.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ms.ais.weather.model.conditions.Conditions;
import ms.ais.weather.model.enums.WeatherForecastType;
import ms.ais.weather.model.location.CityGeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public abstract class WeatherForecast {

    @JsonProperty("conditions")
    protected List<Conditions> conditionsList;
    protected CityGeoPoint cityGeoPoint;
    protected WeatherForecastType type;
    protected long timestamp;

    protected WeatherForecast(WeatherForecastType type) {
        this.type = type;
    }

    public List<Conditions> getConditionsList() {
        return conditionsList;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public WeatherForecastType getType() {
        return type;
    }

    public CityGeoPoint getCityGeoPoint() {
        return cityGeoPoint;
    }

    public abstract static class Builder {

        protected final List<Conditions> conditionsList;
        protected long timestamp;
        protected CityGeoPoint cityGeoPoint;

        public Builder() {
            conditionsList = new ArrayList<>();
        }

        public Builder addConditions(Conditions conditions) {
            if (!Objects.isNull(conditions)) {
                conditionsList.add(conditions);
            }
            return this;
        }

        public Builder withTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withCityGeoPoint(CityGeoPoint cityGeoPoint) {
            this.cityGeoPoint = cityGeoPoint;
            return this;
        }

        public abstract WeatherForecast build();

    }
}
