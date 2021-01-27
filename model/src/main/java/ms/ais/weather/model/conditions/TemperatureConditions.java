package ms.ais.weather.model.conditions;

import ms.ais.weather.model.conditions.enums.ConditionsType;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public abstract class TemperatureConditions extends Conditions {

    protected final double temperature;
    protected final double feelsLike;
    protected final double maxTemperature;
    protected final double minTemperature;

    protected TemperatureConditions(Builder builder) {
        super(ConditionsType.TEMPERATURE);
        this.temperature = builder.temperature;
        this.feelsLike = builder.feelsLike;
        this.maxTemperature = builder.maxTemperature;
        this.minTemperature = builder.minTemperature;
    }

    public abstract static class Builder {

        protected double temperature;
        protected double feelsLike;
        protected double maxTemperature;
        protected double minTemperature;

        public Builder temperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder feelsLike(double temperature) {
            this.feelsLike = temperature;
            return this;
        }

        public Builder maxTemperature(double maxTemperature) {
            this.maxTemperature = maxTemperature;
            return this;
        }

        public Builder minTemperature(double minTemperature) {
            this.minTemperature = minTemperature;
            return this;
        }

        abstract TemperatureConditions build();
    }

}
