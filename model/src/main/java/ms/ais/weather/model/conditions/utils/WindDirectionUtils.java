package ms.ais.weather.model.conditions.utils;

import ms.ais.weather.model.conditions.enums.WindDirection;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 23/1/2021.
 */
public class WindDirectionUtils {

    public static WindDirection extractWindDirection(double windDegrees) {

        if ((windDegrees > 348.75 && windDegrees <= 360) ||
            (windDegrees >= 0 && windDegrees < 11.25)) {
            return WindDirection.NORTH;
        } else if (windDegrees >= 11.25 && windDegrees < 33.75) {
            return WindDirection.NORTH_NORTH_EAST;
        } else if (windDegrees >= 33.75 && windDegrees < 56.25) {
            return WindDirection.NORTH_EAST;
        } else if (windDegrees >= 56.25 && windDegrees < 78.75) {
            return WindDirection.EAST_NORTH_EAST;
        } else if (windDegrees >= 78.75 && windDegrees < 101.25) {
            return WindDirection.EAST;
        } else if (windDegrees >= 101.25 && windDegrees < 123.75) {
            return WindDirection.EAST_SOUTH_EAST;
        } else if (windDegrees >= 123.75 && windDegrees < 146.25) {
            return WindDirection.SOUTH_EAST;
        } else if (windDegrees >= 146.25 && windDegrees < 168.75) {
            return WindDirection.SOUTH_SOUTH_EAST;
        } else if (windDegrees >= 168.75 && windDegrees < 191.25) {
            return WindDirection.SOUTH;
        } else if (windDegrees >= 191.25 && windDegrees < 213.75) {
            return WindDirection.SOUTH_SOUTH_WEST;
        } else if (windDegrees >= 213.75 && windDegrees < 236.25) {
            return WindDirection.SOUTH_WEST;
        } else if (windDegrees >= 236.25 && windDegrees < 258.75) {
            return WindDirection.WEST_SOUTH_WEST;
        } else if (windDegrees >= 258.75 && windDegrees < 281.25) {
            return WindDirection.WEST;
        } else if (windDegrees >= 281.25 && windDegrees < 303.75) {
            return WindDirection.WEST_NORTH_WEST;
        } else if (windDegrees >= 303.75 && windDegrees < 326.25) {
            return WindDirection.NORTH_WEST;
        } else if (windDegrees >= 326.25 && windDegrees < 348.75) {
            return WindDirection.NORTH_NORTH_WEST;
        } else {
            throw new IllegalArgumentException("Error... Invalid windDegrees value. Values allowed [0, 360]");
        }

    }

}
