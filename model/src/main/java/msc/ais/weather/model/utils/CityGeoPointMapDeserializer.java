package msc.ais.weather.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import msc.ais.weather.model.location.CityGeoPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public class CityGeoPointMapDeserializer extends JsonDeserializer<Map<String, CityGeoPoint>> {

    @Override
    public Map<String, CityGeoPoint> deserialize(JsonParser jsonParser,
                                                 DeserializationContext context)
        throws IOException {

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode cityGeoPointJsonArray = mapper.readTree(jsonParser);
        final Map<String, CityGeoPoint> cityGeoPointMap = new HashMap<>();

        for (JsonNode cityGeoPointNode : cityGeoPointJsonArray) {

            String cityName = cityGeoPointNode.path("cityName").asText();
            double longitude = cityGeoPointNode.path("longitude").asDouble();
            double latitude = cityGeoPointNode.path("latitude").asDouble();

            CityGeoPoint cityGeoPoint = CityGeoPoint.builder()
                .withCityName(cityName)
                .withLongitude(longitude)
                .withLatitude(latitude).build();

            cityGeoPointMap.put(cityName, cityGeoPoint);
        }

        return cityGeoPointMap;
    }
}
