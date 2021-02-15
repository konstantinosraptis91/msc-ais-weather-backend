package msc.ais.weather.model.conditions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import msc.ais.weather.model.conditions.enums.WindDirection;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 23/1/2021.
 */
@JsonSerialize(as = DailyWindConditions.class)
public interface DailyWindConditions {

    WindDirection getWindDirection();

    double getWindSpeed();

}
