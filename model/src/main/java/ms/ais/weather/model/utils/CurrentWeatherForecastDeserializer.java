package ms.ais.weather.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.ais.weather.model.CurrentWeatherForecast;
import ms.ais.weather.model.conditions.TemperatureConditions;
import ms.ais.weather.model.conditions.WeatherConditions;
import ms.ais.weather.model.conditions.WindConditions;
import ms.ais.weather.model.location.CityGeoPoint;

import java.io.IOException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 19/1/2021.
 */
public class CurrentWeatherForecastDeserializer extends JsonDeserializer<CurrentWeatherForecast> {

    @Override
    public CurrentWeatherForecast deserialize(JsonParser jsonParser,
                                              DeserializationContext context) throws IOException, JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode currentWeatherJson = mapper.readTree(jsonParser);
        long timestamp = currentWeatherJson.path("dt").asLong();

        return CurrentWeatherForecast.builder()
            .withCityGeoPoint(extractCityGeoPoint(currentWeatherJson))
            .addConditions(extractTemperatureConditions(currentWeatherJson))
            .addConditions(extractWindConditions(currentWeatherJson))
            .addConditions(extractWeatherConditions(currentWeatherJson))
            .withTimestamp(timestamp)
            .build();
    }

    private CityGeoPoint extractCityGeoPoint(final JsonNode currentWeatherJson) {
        double longitude = currentWeatherJson.path("coord").path("lon").asDouble();
        double latitude = currentWeatherJson.path("coord").path("lat").asDouble();
        String cityName = currentWeatherJson.path("name").asText();

        return CityGeoPoint.builder()
            .withLongitude(longitude)
            .withLatitude(latitude)
            .withCityName(cityName)
            .build();
    }

    private WeatherConditions extractWeatherConditions(final JsonNode currentWeatherJson) {
        final JsonNode weatherJsonArray = currentWeatherJson.path("weather");
        final JsonNode firstObject = weatherJsonArray.iterator().next();
        String main = firstObject.path("main").asText();
        String description = firstObject.path("description").asText();
        int cloudPercentage = currentWeatherJson.path("clouds").path("all").asInt();

        return WeatherConditions.builder()
            .main(main)
            .description(description)
            .cloudPercentage(cloudPercentage)
            .build();
    }

    private WindConditions extractWindConditions(final JsonNode currentWeatherJson) {
        double windSpeed = currentWeatherJson.path("wind").path("speed").asDouble();
        double windDegrees = currentWeatherJson.path("wind").path("deg").asInt();

        return WindConditions.builder()
            .windSpeed(windSpeed)
            .windDegrees(windDegrees)
            .build();
    }

    private TemperatureConditions extractTemperatureConditions(final JsonNode currentWeatherJson) {
        double temperature = currentWeatherJson.path("main").path("temp").asDouble();
        double feelsLike = currentWeatherJson.path("main").path("feels_like").asDouble();

        return TemperatureConditions.builder()
            .temperature(temperature)
            .feelsLike(feelsLike)
            .build();
    }

}
