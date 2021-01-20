package ms.ais.weather.model.conditions;

import ms.ais.weather.model.conditions.enums.ConditionsType;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public class WindConditions extends Conditions {

    private final double windSpeed;
    private final double windDegrees;

    private WindConditions(Builder builder) {
        super(ConditionsType.WIND);
        this.windSpeed = builder.windSpeed;
        this.windDegrees = builder.windDegrees;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDegrees() {
        return windDegrees;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private double windSpeed;
        private double windDegrees;

        public Builder windSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }

        public Builder windDegrees(double windDegrees) {
            this.windDegrees = windDegrees;
            return this;
        }

        public WindConditions build() {
            return new WindConditions(this);
        }

    }

    @Override
    public String toString() {
        return "WindConditions{" +
            "windSpeed=" + windSpeed +
            ", windDegrees=" + windDegrees +
            '}';
    }
}
