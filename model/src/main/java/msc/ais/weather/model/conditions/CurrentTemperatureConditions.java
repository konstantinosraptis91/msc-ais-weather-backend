package msc.ais.weather.model.conditions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 22/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
@JsonSerialize(as = CurrentTemperatureConditions.class)
public interface CurrentTemperatureConditions {

    double getTemperature();

    double getFeelsLike();

    double getMaxTemperature();

    double getMinTemperature();

}
