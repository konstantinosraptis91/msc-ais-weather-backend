package msc.ais.weather.model.utils;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 2/2/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public enum CodelistsOfBiMap {

    COUNTRY_CODE_MAP("code", "name", "/json/country-code-map.json");

    private final String codeName;
    private final String valueName;
    private final String filename;

    private volatile GenericBiMapCode INSTANCE;

    CodelistsOfBiMap(String codeName, String valueName, String filename) {
        this.codeName = codeName;
        this.valueName = valueName;
        this.filename = filename;
    }

    private GenericBiMapCode getInstance() {
        if (Objects.isNull(INSTANCE)) {
            makeInstance();
        }
        return INSTANCE;
    }

    protected synchronized void makeInstance() {
        INSTANCE = new GenericBiMapCode(codeName, valueName, filename);
    }

    public final String getValueForId(String id) {
        return getInstance().getValueForId(id);
    }

    public final String getIdForValue(String value) {
        return getInstance().getIdForValue(value);
    }

    public final boolean containsValue(String value) {
        return getInstance().containsValue(value);
    }

    public final boolean containsKey(String key) {
        return getInstance().containsKey(key);
    }

    public final Optional<String> getOptionalValueForId(String id) {
        return Optional.ofNullable(getValueForId(id));
    }

    public final Optional<String> getOptionalIdForValue(String value) {
        return Optional.ofNullable(getIdForValue(value));

    }
}
