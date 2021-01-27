package ms.ais.weather.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.ais.weather.model.HourlyWeatherForecast;
import ms.ais.weather.model.conditions.*;
import ms.ais.weather.model.location.CityGeoPoint;
import ms.ais.weather.model.response.HourlyWeatherForecastResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 22/1/2021.
 */
public class HourlyWeatherForecastResponseDeserializer extends JsonDeserializer<HourlyWeatherForecastResponse> {

    @Override
    public HourlyWeatherForecastResponse deserialize(JsonParser jsonParser,
                                                     DeserializationContext context) throws IOException, JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode hourlyWeatherJsonRootObj = mapper.readTree(jsonParser);
        final JsonNode hourlyWeatherJsonArray = hourlyWeatherJsonRootObj.path("hourly");
        final HourlyWeatherForecastResponse response =
            HourlyWeatherForecastResponse.createInstance(extractCityGeoPoint(hourlyWeatherJsonRootObj));
        final List<HourlyWeatherForecast> forecastList = new ArrayList<>();

        for (JsonNode hourlyWeatherJson : hourlyWeatherJsonArray) {

            HourlyWeatherForecast forecast = HourlyWeatherForecast.builder()
                .timestamp(extractTimestamp(hourlyWeatherJson))
                .weatherConditions(extractHourlyWeatherConditions(hourlyWeatherJson))
                .temperatureConditions(extractHourlyTemperatureConditions(hourlyWeatherJson))
                .build();

            forecastList.add(forecast);
        }

        response.getForecastList().addAll(forecastList);
        return response;
    }

    private CityGeoPoint extractCityGeoPoint(final JsonNode hourlyWeatherJsonRootObj) {
        double longitude = hourlyWeatherJsonRootObj.path("lon").asDouble();
        double latitude = hourlyWeatherJsonRootObj.path("lat").asDouble();
        String cityName = hourlyWeatherJsonRootObj.path("timezone").asText();

        return CityGeoPoint.builder()
            .withLongitude(longitude)
            .withLatitude(latitude)
            .withCityName(cityName)
            .build();
    }

    private long extractTimestamp(final JsonNode hourlyWeatherJson) {
        return hourlyWeatherJson.path("dt").asLong();
    }

    private HourlyWeatherConditions extractHourlyWeatherConditions(final JsonNode hourlyWeatherJson) {
        final JsonNode weatherJsonArray = hourlyWeatherJson.path("weather");
        final JsonNode firstObject = weatherJsonArray.iterator().next();
        String description = firstObject.path("description").asText();

        return HourlyWeatherConditionsImpl.builder()
            .description(description)
            .build();
    }

    private HourlyTemperatureConditions extractHourlyTemperatureConditions(final JsonNode hourlyWeatherJson) {
        double temperature = hourlyWeatherJson.path("temp").asDouble();

        return HourlyTemperatureConditionsImpl.builder()
            .temperature(temperature)
            .build();
    }

}
