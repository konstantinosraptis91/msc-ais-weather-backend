package ms.ais.weather.retriever;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ms.ais.weather.model.enums.WeatherForecastType;
import ms.ais.weather.model.response.CurrentWeatherForecastResponse;
import ms.ais.weather.model.response.DailyWeatherForecastResponse;
import ms.ais.weather.model.response.HourlyWeatherForecastResponse;
import ms.ais.weather.model.utils.CurrentWeatherForecastResponseDeserializer;
import ms.ais.weather.model.utils.DailyWeatherForecastResponseDeserializer;
import ms.ais.weather.model.utils.HourlyWeatherForecastResponseDeserializer;
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
    public CurrentWeatherForecastResponse getCurrentWeatherForecastResponse(String cityName) {

        GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.newInstance(
            OpenWeatherMapURI.builder()
                .withCityName(cityName)
                .withKey("200681ee8b9be15aafc017130d88cd41")
                .withUnitsType(UnitsType.METRIC)
                .withWeatherForecastType(WeatherForecastType.CURRENT)
                .build());

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CurrentWeatherForecastResponse.class, new CurrentWeatherForecastResponseDeserializer());
        mapper.registerModule(module);

        CurrentWeatherForecastResponse response = null;

        try {
            response = mapper.readValue(task.call(), CurrentWeatherForecastResponse.class);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return response;
    }

    @Override
    public HourlyWeatherForecastResponse getHourlyWeatherForecastResponse(String cityName) {

        GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.newInstance(
            OpenWeatherMapURI.builder()
                .withCityName(cityName)
                .withKey("200681ee8b9be15aafc017130d88cd41")
                .withUnitsType(UnitsType.METRIC)
                .withWeatherForecastType(WeatherForecastType.HOURLY)
                .build());

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(HourlyWeatherForecastResponse.class, new HourlyWeatherForecastResponseDeserializer());
        mapper.registerModule(module);

        HourlyWeatherForecastResponse response = null;

        try {
            response = mapper.readValue(task.call(), HourlyWeatherForecastResponse.class);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return response;
    }

    @Override
    public DailyWeatherForecastResponse getDailyWeatherForecastResponse(String cityName) {

        GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.newInstance(
            OpenWeatherMapURI.builder()
                .withCityName(cityName)
                .withKey("200681ee8b9be15aafc017130d88cd41")
                .withUnitsType(UnitsType.METRIC)
                .withWeatherForecastType(WeatherForecastType.DAILY)
                .build());

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        CurrentWeatherForecastResponse currentWeatherForecastResponse = getCurrentWeatherForecastResponse(cityName);
        module.addDeserializer(DailyWeatherForecastResponse.class,
            new DailyWeatherForecastResponseDeserializer(
                currentWeatherForecastResponse
                    .getForecast()
                    .getTemperatureConditions()));
        mapper.registerModule(module);

        DailyWeatherForecastResponse response = null;

        try {
            response = mapper.readValue(task.call(), DailyWeatherForecastResponse.class);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return response;
    }
}
