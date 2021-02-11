package msc.ais.weather.model.conditions.enums;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 23/1/2021.
 */
public enum WindDirection {

    NORTH("North"), NORTH_WEST("North West"), NORTH_EAST("North East"),
    SOUTH("South"), SOUTH_WEST("South West"), SOUTH_EAST("South East"),
    WEST("West"), EAST("East"),
    NORTH_NORTH_WEST("North/North West"), NORTH_NORTH_EAST("North/North East"),
    SOUTH_SOUTH_WEST("South/South West"), SOUTH_SOUTH_EAST("South/South East"),
    EAST_NORTH_EAST("East/North East"), EAST_SOUTH_EAST("East/South East"),
    WEST_SOUTH_WEST("West/South West"), WEST_NORTH_WEST("West/North West");

    private final String value;

    WindDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
