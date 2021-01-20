package ms.ais.weather.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.ais.weather.model.location.CityGeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public class CityGeoPointListDeserializer extends JsonDeserializer<List<CityGeoPoint>> {

    @Override
    public List<CityGeoPoint> deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode cityGeoPointJsonArray = mapper.readTree(jsonParser);
        final List<CityGeoPoint> cityGeoPointList = new ArrayList<>();

        for (JsonNode cityGeoPointNode : cityGeoPointJsonArray) {

            String cityName = cityGeoPointNode.path("cityName").asText();
            double longitude = cityGeoPointNode.path("longitude").asDouble();
            double latitude = cityGeoPointNode.path("latitude").asDouble();

            CityGeoPoint cityGeoPoint = CityGeoPoint.builder()
                .withCityName(cityName)
                .withLongitude(longitude)
                .withLatitude(latitude).build();

            cityGeoPointList.add(cityGeoPoint);
        }

        return cityGeoPointList;
    }
}
