package ms.ais.weather.service.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms.ais.weather.model.enums.WeatherForecastType;
import ms.ais.weather.service.enums.UnitsType;
import org.junit.jupiter.api.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public class GetFromOpenWeatherMapTaskTest {

    @Test
    public void testCall() throws Exception {

        GetFromOpenWeatherMapTask task = GetFromOpenWeatherMapTask.createWithURI(
            OpenWeatherMapURI.builder()
                .withCityName("Athens")
                .withKey("200681ee8b9be15aafc017130d88cd41")
                .withUnitsType(UnitsType.METRIC)
                .withWeatherForecastType(WeatherForecastType.DAILY)
                .build().getURI());

        // task.getOpenWeatherMapURI().printURI();

        ObjectMapper mapper = new ObjectMapper();
        Object prettyObj = mapper.readValue(task.call(), Object.class);
        String pretty = mapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(prettyObj);
        System.out.println(pretty);
    }

}
