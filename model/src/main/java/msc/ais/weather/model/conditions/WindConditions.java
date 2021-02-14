package msc.ais.weather.model.conditions;

import msc.ais.weather.model.conditions.enums.ConditionsType;
import msc.ais.weather.model.conditions.enums.WindDirection;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public abstract class WindConditions extends Conditions {

    protected final double windSpeed;
    protected final WindDirection windDirection;

    protected WindConditions(Builder builder) {
        super(ConditionsType.WIND);
        this.windSpeed = builder.windSpeed;
        this.windDirection = builder.windDirection;
    }

    public abstract static class Builder {

        protected double windSpeed;
        protected WindDirection windDirection;

        public Builder windSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }

        public Builder windDirection(WindDirection windDirection) {
            this.windDirection = windDirection;
            return this;
        }

        abstract WindConditions build();
    }

}
