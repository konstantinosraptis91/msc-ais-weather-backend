package ms.ais.weather.service.enums;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public enum UnitsType {

    STANDARD("standard"), METRIC("metric"), IMPERIAL("imperial");

    private final String value;

    UnitsType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
