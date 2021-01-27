package ms.ais.weather.model.conditions;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 25/1/2021.
 */
public class HourlyWeatherConditionsImpl
    extends WeatherConditions
    implements HourlyWeatherConditions {

    private HourlyWeatherConditionsImpl(Builder builder) {
        super(builder);
    }

    @Override
    public String getDescription() {
        return description;
    }
}
