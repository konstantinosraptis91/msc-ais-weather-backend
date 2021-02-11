package msc.ais.weather.model.conditions;

import msc.ais.weather.model.conditions.enums.WindDirection;

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

    @Override
    public String toString() {
        return "DailyWindConditionsImpl{" +
            "type=" + type +
            ", windSpeed=" + windSpeed +
            ", windDirection=" + windDirection +
            '}';
    }
}
