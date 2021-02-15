package msc.ais.weather.model.conditions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 22/1/2021.
 */
@JsonSerialize(as = HourlyWeatherConditions.class)
public interface HourlyWeatherConditions {

    String getDescription();

}
