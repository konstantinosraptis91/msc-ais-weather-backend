package ms.ais.weather.model.conditions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 23/1/2021.
 */
@JsonSerialize(as = DailyWeatherConditions.class)
public interface DailyWeatherConditions {

    String getDescription();

    int getCloudPercentage();

    int getHumidityPercentage();

    double getRainProbability();

}
