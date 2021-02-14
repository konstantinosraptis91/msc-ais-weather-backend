package msc.ais.weather.model.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.BiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 2/2/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public class GenericBiMapCode {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericBiMapCode.class);

    protected BiMap<String, String> biMap;

    protected GenericBiMapCode(String codeName, String valueName, String filename) {

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BiMap.class, new CodelistsBiMapDeserializer(codeName, valueName));
        mapper.registerModule(module);

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(GenericBiMapCode.class.getResourceAsStream(filename)))) {

            biMap = mapper.readValue(reader, new TypeReference<>() {
            });

        } catch (
            JsonParseException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (
            JsonMappingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    protected final String getValueForId(String id) {
        return biMap.get(id);
    }

    protected final String getIdForValue(String value) {
        return biMap.inverse().get(value);
    }

    protected final boolean containsValue(String value) {
        return biMap.containsValue(value);
    }

    protected final boolean containsKey(String key) {
        return biMap.containsKey(key);
    }
}
