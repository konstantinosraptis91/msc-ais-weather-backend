package msc.ais.weather.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import msc.ais.weather.model.location.City;
import msc.ais.weather.model.location.CityGeoPoint;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 1/2/2021.
 */
public class GeocodingCityDeserializer extends JsonDeserializer<City> {

    @Override
    public City deserialize(JsonParser jsonParser,
                            DeserializationContext context)
        throws IOException {

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode cityGeocodingRootArray = mapper.readTree(jsonParser);
        final JsonNode firstCityJsonObject = cityGeocodingRootArray.iterator().next();

        final String name = extractCityName(firstCityJsonObject);
        final String country2ACode = firstCityJsonObject.path("country").asText();
        final double longitude = firstCityJsonObject.path("lon").asDouble();
        final double latitude = firstCityJsonObject.path("lat").asDouble();

        return City.builder()
            .cityGeoPoint(CityGeoPoint.builder()
                .withCityName(name)
                .withLongitude(longitude)
                .withLatitude(latitude)
                .build())
            .country(CodelistsOfBiMap.COUNTRY_CODE_MAP.getValueForId(country2ACode))
            .build();
    }

    private String extractCityName(JsonNode firstCityJsonObject) {
        String enName = firstCityJsonObject.path("local_names").path("en").asText();
        String asciiName = firstCityJsonObject.path("local_names").path("ascii").asText();
        return Objects.isNull(enName) || enName.isEmpty() || enName.isBlank() ? asciiName : enName;
    }

}
