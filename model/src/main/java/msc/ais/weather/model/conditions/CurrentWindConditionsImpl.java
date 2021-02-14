package msc.ais.weather.model.conditions;

import msc.ais.weather.model.conditions.enums.WindDirection;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 25/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public class CurrentWindConditionsImpl
    extends WindConditions
    implements CurrentWindConditions {

    private CurrentWindConditionsImpl(Builder builder) {
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
        public CurrentWindConditionsImpl build() {
            return new CurrentWindConditionsImpl(this);
        }

    }

    @Override
    public String toString() {
        return "CurrentWindConditionsImpl{" +
            "type=" + type +
            ", windSpeed=" + windSpeed +
            ", windDirection=" + windDirection +
            '}';
    }
}
