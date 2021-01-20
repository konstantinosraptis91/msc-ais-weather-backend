package ms.ais.weather.model.conditions;

import ms.ais.weather.model.conditions.enums.ConditionsType;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public abstract class Conditions {

    protected ConditionsType type;

    protected Conditions(ConditionsType type) {
        this.type = type;
    }

    public ConditionsType getType() {
        return type;
    }
}
