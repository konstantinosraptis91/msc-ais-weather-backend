package ms.ais.weather.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.ais.weather.model.db.City;
import ms.ais.weather.model.location.CityGeoPoint;

import java.io.IOException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 1/2/2021.
 */
public class GeocodingCityDeserializer extends JsonDeserializer<City> {

    @Override
    public City deserialize(JsonParser jsonParser,
                            DeserializationContext context) throws IOException, JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode cityGeocodingRootArray = mapper.readTree(jsonParser);
        final JsonNode firstCityJsonObject = cityGeocodingRootArray.iterator().next();

        final String name = firstCityJsonObject.path("name").asText();
        final String country = firstCityJsonObject.path("country").asText();
        final double longitude = firstCityJsonObject.path("lon").asDouble();
        final double latitude = firstCityJsonObject.path("lat").asDouble();

        return City.builder()
            .cityGeoPoint(CityGeoPoint.builder()
                .withCityName(name)
                .withLongitude(longitude)
                .withLatitude(latitude)
                .build())
            .country(country)
            .build();
    }
}
