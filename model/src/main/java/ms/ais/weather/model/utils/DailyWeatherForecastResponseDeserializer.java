package ms.ais.weather.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.ais.weather.model.DailyWeatherForecast;
import ms.ais.weather.model.conditions.*;
import ms.ais.weather.model.conditions.utils.WindDirectionUtils;
import ms.ais.weather.model.location.CityGeoPoint;
import ms.ais.weather.model.response.DailyWeatherForecastResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 23/1/2021.
 */
public class DailyWeatherForecastResponseDeserializer extends JsonDeserializer<DailyWeatherForecastResponse> {

    private final CurrentTemperatureConditions temperatureConditions;

    public DailyWeatherForecastResponseDeserializer(CurrentTemperatureConditions temperatureConditions) {
        this.temperatureConditions = temperatureConditions;
    }

    @Override
    public DailyWeatherForecastResponse deserialize(JsonParser jsonParser,
                                                    DeserializationContext context) throws IOException, JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode dailyWeatherJsonRootObj = mapper.readTree(jsonParser);
        final JsonNode dailyWeatherJsonArray = dailyWeatherJsonRootObj.path("daily");
        final DailyWeatherForecastResponse response = DailyWeatherForecastResponse.builder()
            .cityGeoPoint(extractCityGeoPoint(dailyWeatherJsonRootObj))
            .currentTemperatureConditions(temperatureConditions)
            .build();
        final List<DailyWeatherForecast> forecastList = new ArrayList<>();

        for (JsonNode dailyWeatherJson : dailyWeatherJsonArray) {

            DailyWeatherForecast forecast = DailyWeatherForecast.builder()
                .timestamp(extractTimestamp(dailyWeatherJson))
                .temperatureConditions(extractDailyTemperatureConditions(dailyWeatherJson))
                .weatherConditions(extractDailyWeatherConditions(dailyWeatherJson))
                .windConditions(extractDailyWindConditions(dailyWeatherJson))
                .build();

            forecastList.add(forecast);
        }

        response.getForecastList().addAll(forecastList);
        return response;
    }

    private CityGeoPoint extractCityGeoPoint(final JsonNode dailyWeatherJsonRootObj) {
        double longitude = dailyWeatherJsonRootObj.path("lon").asDouble();
        double latitude = dailyWeatherJsonRootObj.path("lat").asDouble();
        String cityName = dailyWeatherJsonRootObj.path("timezone").asText();

        return CityGeoPoint.builder()
            .withLongitude(longitude)
            .withLatitude(latitude)
            .withCityName(cityName)
            .build();
    }

    private long extractTimestamp(final JsonNode dailyWeatherJson) {
        return dailyWeatherJson.path("dt").asLong();
    }

    private DailyWindConditions extractDailyWindConditions(final JsonNode dailyWeatherJson) {
        double windSpeed = dailyWeatherJson.path("wind_speed").asDouble();
        double windDegrees = dailyWeatherJson.path("wind_deg").asDouble();

        return DailyWindConditionsImpl.builder()
            .windSpeed(windSpeed)
            .windDirection(WindDirectionUtils.extractWindDirection(windDegrees))
            .build();
    }

    private DailyWeatherConditions extractDailyWeatherConditions(final JsonNode dailyWeatherJson) {
        final JsonNode weatherJsonArray = dailyWeatherJson.path("weather");
        final JsonNode firstObject = weatherJsonArray.iterator().next();
        String description = firstObject.path("description").asText();
        double rainProbability = dailyWeatherJson.path("pop").asDouble();
        int humidityPercentage = dailyWeatherJson.path("humidity").asInt();
        int cloudPercentage = dailyWeatherJson.path("clouds").asInt();

        return DailyWeatherConditionsImpl.builder()
            .description(description)
            .rainProbability(rainProbability)
            .humidityPercentage(humidityPercentage)
            .cloudPercentage(cloudPercentage)
            .build();
    }

    private DailyTemperatureConditions extractDailyTemperatureConditions(final JsonNode dailyWeatherJson) {
        double maxTemperature = dailyWeatherJson.path("temp").path("max").asDouble();
        double minTemperature = dailyWeatherJson.path("temp").path("min").asDouble();

        return DailyTemperatureConditionsImpl.builder()
            .maxTemperature(maxTemperature)
            .minTemperature(minTemperature)
            .build();
    }
}
