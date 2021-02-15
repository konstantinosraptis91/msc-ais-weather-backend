package msc.ais.weather.model.conditions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import msc.ais.weather.model.conditions.enums.WindDirection;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 24/1/2021.
 */
@JsonSerialize(as = CurrentWindConditions.class)
public interface CurrentWindConditions {

    WindDirection getWindDirection();

    double getWindSpeed();

}
