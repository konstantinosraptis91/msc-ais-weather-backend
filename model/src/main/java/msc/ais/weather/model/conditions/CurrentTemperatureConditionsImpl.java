package msc.ais.weather.model.conditions;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 25/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public class CurrentTemperatureConditionsImpl
    extends TemperatureConditions
    implements CurrentTemperatureConditions {

    private CurrentTemperatureConditionsImpl(Builder builder) {
        super(builder);
    }

    @Override
    public double getTemperature() {
        return temperature;
    }

    @Override
    public double getFeelsLike() {
        return feelsLike;
    }

    @Override
    public double getMaxTemperature() {
        return maxTemperature;
    }

    @Override
    public double getMinTemperature() {
        return minTemperature;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends TemperatureConditions.Builder {

        @Override
        public Builder temperature(double temperature) {
            super.temperature(temperature);
            return this;
        }

        @Override
        public Builder feelsLike(double feelsLike) {
            super.feelsLike(feelsLike);
            return this;
        }

        @Override
        public Builder maxTemperature(double maxTemperature) {
            super.maxTemperature(maxTemperature);
            return this;
        }

        @Override
        public Builder minTemperature(double minTemperature) {
            super.minTemperature(minTemperature);
            return this;
        }

        @Override
        public CurrentTemperatureConditionsImpl build() {
            return new CurrentTemperatureConditionsImpl(this);
        }
    }

    @Override
    public String toString() {
        return "CurrentTemperatureConditionsImpl{" +
            "type=" + type +
            ", temperature=" + temperature +
            ", feelsLike=" + feelsLike +
            ", maxTemperature=" + maxTemperature +
            ", minTemperature=" + minTemperature +
            '}';
    }
}
