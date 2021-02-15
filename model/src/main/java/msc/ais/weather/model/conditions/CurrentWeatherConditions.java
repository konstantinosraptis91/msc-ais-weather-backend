package msc.ais.weather.model.conditions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 24/1/2021.
 */
@JsonSerialize(as = CurrentWeatherConditions.class)
public interface CurrentWeatherConditions {

    String getDescription();

    int getCloudPercentage();

    int getHumidityPercentage();

    // double getRainProbability();

}
