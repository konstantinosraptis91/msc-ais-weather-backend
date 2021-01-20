package ms.ais.weather.retriever;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ms.ais.weather.model.CurrentWeatherForecast;
import ms.ais.weather.model.DailyWeatherForecast;
import ms.ais.weather.model.HourlyWeatherForecast;
import ms.ais.weather.model.enums.WeatherForecastType;
import ms.ais.weather.model.utils.CurrentWeatherForecastDeserializer;
import ms.ais.weather.retriever.enums.UnitsType;
import ms.ais.weather.retriever.tasks.GetFromOpenWeatherMapTask;
import ms.ais.weather.retriever.tasks.OpenWeatherMapURI;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 19/1/2021.
 */
public class OpenWeatherMapDataRetriever implements WeatherDataRetriever {

    private static final Logger LOGGER = Logger.getLogger(OpenWeatherMapDataRetriever.class.getName());

    @Override
    public CurrentWeatherForecast getCurrentWeatherForecast() {

        GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.newInstance(
            OpenWeatherMapURI.builder()
                .withCityName("Athens")
                .withKey("200681ee8b9be15aafc017130d88cd41")
                .withUnitsType(UnitsType.METRIC)
                .withWeatherForecastType(WeatherForecastType.CURRENT)
                .build());

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CurrentWeatherForecast.class, new CurrentWeatherForecastDeserializer());
        mapper.registerModule(module);

        CurrentWeatherForecast weatherForecast = null;

        try {
            weatherForecast = mapper.readValue(task.call(), CurrentWeatherForecast.class);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return weatherForecast;
    }

    @Override
    public HourlyWeatherForecast getHourlyWeatherForecast() {
        return null;
    }

    @Override
    public DailyWeatherForecast getDailyWeatherForecast() {
        return null;
    }

}
