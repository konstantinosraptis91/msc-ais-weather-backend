package msc.ais.weather.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.io.IOException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 2/2/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public class CodelistsBiMapDeserializer extends JsonDeserializer<BiMap<String, String>> {

    private final String codeName;
    private final String valueName;

    public CodelistsBiMapDeserializer(String codeName, String valueName) {
        this.codeName = codeName;
        this.valueName = valueName;
    }

    @Override
    public BiMap<String, String> deserialize(JsonParser jsonParser,
                                             DeserializationContext context)
        throws IOException {

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode codelistMapJsonArray = mapper.readTree(jsonParser);
        final BiMap<String, String> biMap = HashBiMap.create();

        for (JsonNode codelistMapNode : codelistMapJsonArray) {

            String code = codelistMapNode.path(codeName).asText();
            String value = codelistMapNode.path(valueName).asText();

            biMap.put(code, value);
        }

        return biMap;
    }

}
