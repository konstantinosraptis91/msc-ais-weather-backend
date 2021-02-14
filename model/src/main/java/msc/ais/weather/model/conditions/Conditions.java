package msc.ais.weather.model.conditions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import msc.ais.weather.model.conditions.enums.ConditionsType;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public abstract class Conditions {

    @JsonIgnore
    protected ConditionsType type;

    protected Conditions(ConditionsType type) {
        this.type = type;
    }

    public ConditionsType getType() {
        return type;
    }
}
