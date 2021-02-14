package msc.ais.weather.model.conditions;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 25/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public class HourlyTemperatureConditionsImpl
    extends TemperatureConditions
    implements HourlyTemperatureConditions {

    private HourlyTemperatureConditionsImpl(Builder builder) {
        super(builder);
    }

    @Override
    public double getTemperature() {
        return temperature;
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
        public HourlyTemperatureConditionsImpl build() {
            return new HourlyTemperatureConditionsImpl(this);
        }
    }

    @Override
    public String toString() {
        return "HourlyTemperatureConditionsImpl{" +
            "type=" + type +
            ", temperature=" + temperature +
            ", feelsLike=" + feelsLike +
            ", maxTemperature=" + maxTemperature +
            ", minTemperature=" + minTemperature +
            '}';
    }
}
