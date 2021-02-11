package ms.ais.weather.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.ais.weather.model.location.City;
import ms.ais.weather.model.location.CityGeoPoint;

import java.io.IOException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 4/2/2021.
 */
public class IPStackCityDeserializer extends JsonDeserializer<City> {

    @Override
    public City deserialize(JsonParser jsonParser,
                            DeserializationContext context) throws IOException, JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode ipStackRootJsonObject = mapper.readTree(jsonParser);

        final String name = ipStackRootJsonObject.path("city").asText();
        final String country = ipStackRootJsonObject.path("country_name").asText();
        final double longitude = ipStackRootJsonObject.path("longitude").asDouble();
        final double latitude = ipStackRootJsonObject.path("latitude").asDouble();

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
