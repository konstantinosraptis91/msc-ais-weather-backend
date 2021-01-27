package ms.ais.weather.model.conditions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ms.ais.weather.model.conditions.enums.WindDirection;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 25/1/2021.
 */
public class DailyWindConditionsImpl
    extends WindConditions
    implements DailyWindConditions {

    private DailyWindConditionsImpl(Builder builder) {
        super(builder);
    }

    @Override
    public WindDirection getWindDirection() {
        return windDirection;
    }

    @Override
    public double getWindSpeed() {
        return windSpeed;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends WindConditions.Builder {

        @Override
        public Builder windSpeed(double windSpeed) {
            super.windSpeed(windSpeed);
            return this;
        }

        @Override
        public Builder windDirection(WindDirection windDirection) {
            super.windDirection(windDirection);
            return this;
        }

        @Override
        public DailyWindConditionsImpl build() {
            return new DailyWindConditionsImpl(this);
        }
    }
}
