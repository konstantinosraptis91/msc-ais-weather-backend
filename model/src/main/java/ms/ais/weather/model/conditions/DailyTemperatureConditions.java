package ms.ais.weather.model.conditions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 23/1/2021.
 */
@JsonSerialize(as = DailyTemperatureConditions.class)
public interface DailyTemperatureConditions {

    double getMaxTemperature();

    double getMinTemperature();

}
