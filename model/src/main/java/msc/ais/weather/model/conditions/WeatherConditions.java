package msc.ais.weather.model.conditions;

import msc.ais.weather.model.conditions.enums.ConditionsType;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public abstract class WeatherConditions extends Conditions {

    protected final String main;
    protected final String description;
    protected final int cloudPercentage;
    protected final int humidityPercentage;
    protected final double rainProbability;

    protected WeatherConditions(Builder builder) {
        super(ConditionsType.WEATHER);
        this.main = builder.main;
        this.description = builder.description;
        this.cloudPercentage = builder.cloudPercentage;
        this.rainProbability = builder.rainProbability;
        this.humidityPercentage = builder.humidityPercentage;
    }

    public abstract static class Builder {

        protected String main;
        protected String description;
        protected int cloudPercentage;
        protected int humidityPercentage;
        protected double rainProbability;

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

        public Builder humidityPercentage(int humidityPercentage) {
            this.humidityPercentage = humidityPercentage;
            return this;
        }

        public Builder rainProbability(double rainProbability) {
            this.rainProbability = rainProbability;
            return this;
        }

        abstract WeatherConditions build();
    }

}
