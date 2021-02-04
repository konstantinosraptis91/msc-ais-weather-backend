package ms.ais.weather.model.conditions;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 25/1/2021.
 */
public class HourlyWeatherConditionsImpl
        extends WeatherConditions
        implements HourlyWeatherConditions {

    private HourlyWeatherConditionsImpl(Builder builder) {
        super(builder);
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends WeatherConditions.Builder {

        @Override
        public Builder description(String description) {
            super.description(description);
            return this;
        }

        @Override
        public HourlyWeatherConditionsImpl build() {
            return new HourlyWeatherConditionsImpl(this);
        }
    }

    @Override
    public String toString() {
        return "HourlyWeatherConditionsImpl{" +
            "type=" + type +
            ", main='" + main + '\'' +
            ", description='" + description + '\'' +
            ", cloudPercentage=" + cloudPercentage +
            ", humidityPercentage=" + humidityPercentage +
            ", rainProbability=" + rainProbability +
            '}';
    }
}
