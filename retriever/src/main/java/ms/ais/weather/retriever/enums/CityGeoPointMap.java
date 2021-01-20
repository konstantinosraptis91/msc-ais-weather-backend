package ms.ais.weather.retriever.enums;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ms.ais.weather.model.location.CityGeoPoint;
import ms.ais.weather.model.utils.CityGeoPointMapDeserializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public enum CityGeoPointMap {

    INSTANCE("/json/city-geo-point.json");

    public final Logger LOGGER = Logger.getLogger(CityGeoPointMap.class.getName());
    private Map<String, CityGeoPoint> cityGeoPointMap;
    private final String filename;

    CityGeoPointMap(String filename) {
        this.filename = filename;
        load();
    }

    public void load() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Map.class, new CityGeoPointMapDeserializer());
        mapper.registerModule(module);

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(CityGeoPointMap.class.getResourceAsStream(filename)))) {

            cityGeoPointMap = mapper.readValue(reader, new TypeReference<>() {
            });

        } catch (
            JsonParseException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (
            JsonMappingException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void reLoad() {
        cityGeoPointMap.clear();
        load();
    }

    public Optional<CityGeoPoint> getCityGeoPoint(String cityName) {
        return Optional.ofNullable(cityGeoPointMap.get(cityName));
    }

    public Map<String, CityGeoPoint> getCityGeoPointMap() {
        return cityGeoPointMap;
    }
}
