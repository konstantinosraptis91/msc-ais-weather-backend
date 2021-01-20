package ms.ais.weather.model.conditions;

import ms.ais.weather.model.conditions.enums.ConditionsType;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public class TemperatureConditions extends Conditions {

    private final double temperature;
    private final double feelsLike;

    private TemperatureConditions(Builder builder) {
        super(ConditionsType.TEMPERATURE);
        this.temperature = builder.temperature;
        this.feelsLike = builder.feelsLike;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private double temperature;
        private double feelsLike;

        public Builder temperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder feelsLike(double temperature) {
            this.feelsLike = temperature;
            return this;
        }

        public TemperatureConditions build() {
            return new TemperatureConditions(this);
        }

    }

    @Override
    public String toString() {
        return "TemperatureConditions{" +
            "temperature=" + temperature +
            ", feelsLike=" + feelsLike +
            '}';
    }
}
