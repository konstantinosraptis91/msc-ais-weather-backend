package ms.ais.weather.model.conditions;

import ms.ais.weather.model.conditions.enums.ConditionsType;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public class WeatherConditions extends Conditions {

    private final String main;
    private final String description;
    private final int cloudPercentage;

    private WeatherConditions(Builder builder) {
        super(ConditionsType.WEATHER);
        this.main = builder.main;
        this.description = builder.description;
        this.cloudPercentage = builder.cloudPercentage;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public int getCloudPercentage() {
        return cloudPercentage;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String main;
        private String description;
        private int cloudPercentage;

        public Builder main(String main) {
            this.main = main;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder cloudPercentage(int cloudPercentage) {
            this.cloudPercentage = cloudPercentage;
            return this;
        }

        public WeatherConditions build() {
            return new WeatherConditions(this);
        }

    }

    @Override
    public String toString() {
        return "WeatherConditions{" +
            "main='" + main + '\'' +
            ", description='" + description + '\'' +
            ", cloudPercentage=" + cloudPercentage +
            '}';
    }
}
