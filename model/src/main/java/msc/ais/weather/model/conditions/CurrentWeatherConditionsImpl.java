package msc.ais.weather.model.conditions;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 25/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public class CurrentWeatherConditionsImpl
    extends WeatherConditions
    implements CurrentWeatherConditions {

    private CurrentWeatherConditionsImpl(Builder builder) {
        super(builder);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getCloudPercentage() {
        return cloudPercentage;
    }

    @Override
    public int getHumidityPercentage() {
        return humidityPercentage;
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
        public Builder cloudPercentage(int cloudPercentage) {
            super.cloudPercentage(cloudPercentage);
            return this;
        }

        @Override
        public Builder humidityPercentage(int humidityPercentage) {
            super.humidityPercentage(humidityPercentage);
            return this;
        }

        @Override
        public CurrentWeatherConditionsImpl build() {
            return new CurrentWeatherConditionsImpl(this);
        }
    }

    @Override
    public String toString() {
        return "CurrentWeatherConditionsImpl{" +
            "type=" + type +
            ", main='" + main + '\'' +
            ", description='" + description + '\'' +
            ", cloudPercentage=" + cloudPercentage +
            ", humidityPercentage=" + humidityPercentage +
            ", rainProbability=" + rainProbability +
            '}';
    }
}
