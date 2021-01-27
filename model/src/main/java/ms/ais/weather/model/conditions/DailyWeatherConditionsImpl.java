package ms.ais.weather.model.conditions;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 25/1/2021.
 */
public class DailyWeatherConditionsImpl
    extends WeatherConditions
    implements DailyWeatherConditions {

    private DailyWeatherConditionsImpl(Builder builder) {
        super(builder);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getCloudPercentage() {
        return cloudPercentage;
    }

    @Override
    public int getHumidityPercentage() {
        return humidityPercentage;
    }

    @Override
    public double getRainProbability() {
        return rainProbability;
    }
}
