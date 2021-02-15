package msc.ais.weather.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import msc.ais.weather.model.CurrentWeatherForecast;
import msc.ais.weather.model.conditions.utils.WindDirectionUtils;
import msc.ais.weather.model.db.City;
import msc.ais.weather.model.location.CityGeoPoint;
import msc.ais.weather.model.response.CurrentWeatherForecastResponse;
import msc.ais.weather.model.conditions.*;

import java.io.IOException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 19/1/2021.
 */
public class CurrentWeatherForecastResponseDeserializer extends JsonDeserializer<CurrentWeatherForecastResponse> {

    @Override
    public CurrentWeatherForecastResponse deserialize(JsonParser jsonParser,
                                                      DeserializationContext context) throws IOException, JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode currentWeatherJson = mapper.readTree(jsonParser);
        final CurrentWeatherForecast forecast = CurrentWeatherForecast.builder()
            .timestamp(extractTimestamp(currentWeatherJson))
            .temperatureConditions(extractCurrentTemperatureConditions(currentWeatherJson))
            .weatherConditions(extractCurrentWeatherConditions(currentWeatherJson))
            .windConditions(extractCurrentWindConditions(currentWeatherJson))
            .build();

        return CurrentWeatherForecastResponse.builder()
            .city(extractCity(currentWeatherJson))
            .currentWeatherForecast(forecast)
            .build();
    }

    private long extractTimestamp(final JsonNode currentWeatherJson) {
        return currentWeatherJson.path("dt").asLong();
    }

//    private CityGeoPoint extractCityGeoPoint(final JsonNode currentWeatherJson) {
//        double longitude = currentWeatherJson.path("coord").path("lon").asDouble();
//        double latitude = currentWeatherJson.path("coord").path("lat").asDouble();
//        String cityName = currentWeatherJson.path("name").asText();
//
//        return CityGeoPoint.builder()
//            .withLongitude(longitude)
//            .withLatitude(latitude)
//            .withCityName(cityName)
//            .build();
//    }

    private City extractCity(final JsonNode currentWeatherJson) {
        double longitude = currentWeatherJson.path("coord").path("lon").asDouble();
        double latitude = currentWeatherJson.path("coord").path("lat").asDouble();
        String cityName = currentWeatherJson.path("name").asText();

        return City.builder()
            .cityGeoPoint(CityGeoPoint.builder().withLongitude(longitude)
                .withLatitude(latitude)
                .withCityName(cityName)
                .build()).build();
    }

    private CurrentWeatherConditions extractCurrentWeatherConditions(final JsonNode currentWeatherJson) {
        final JsonNode weatherJsonArray = currentWeatherJson.path("weather");
        final JsonNode firstObject = weatherJsonArray.iterator().next();
        String description = firstObject.path("description").asText();
        int cloudPercentage = currentWeatherJson.path("clouds").path("all").asInt();
        int humidityPercentage = currentWeatherJson.path("main").path("humidity").asInt();

        return CurrentWeatherConditionsImpl.builder()
            .description(description)
            .cloudPercentage(cloudPercentage)
            .humidityPercentage(humidityPercentage)
            .build();
    }

    private CurrentWindConditions extractCurrentWindConditions(final JsonNode currentWeatherJson) {
        double windSpeed = currentWeatherJson.path("wind").path("speed").asDouble();
        double windDegrees = currentWeatherJson.path("wind").path("deg").asInt();

        return CurrentWindConditionsImpl.builder()
            .windSpeed(windSpeed)
            .windDirection(WindDirectionUtils.extractWindDirection(windDegrees))
            .build();
    }

    private CurrentTemperatureConditions extractCurrentTemperatureConditions(final JsonNode currentWeatherJson) {
        double temperature = currentWeatherJson.path("main").path("temp").asDouble();
        double feelsLike = currentWeatherJson.path("main").path("feels_like").asDouble();
        double maxTemperature = currentWeatherJson.path("main").path("temp_max").asDouble();
        double minTemperature = currentWeatherJson.path("main").path("temp_min").asDouble();

        return CurrentTemperatureConditionsImpl.builder()
            .temperature(temperature)
            .feelsLike(feelsLike)
            .maxTemperature(maxTemperature)
            .minTemperature(minTemperature)
            .build();
    }

}
