package msc.ais.weather.model.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public class GeoPoint {

    // coordinates as an array of [lon, lat]
    private final List<Double> coordinates;

    protected GeoPoint(double longitude, double latitude) {
        coordinates = ImmutableList.of(longitude, latitude);
    }

    /**
     * Create a new geospatial point.
     *
     * @param longitude The longitude
     * @param latitude  The latitude
     */
    public static GeoPoint of(double longitude, double latitude) {
        return new GeoPoint(longitude, latitude);
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    @JsonIgnore
    public double getLongitude() {
        return coordinates.get(0);
    }

    @JsonIgnore
    public double getLatitude() {
        return coordinates.get(1);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.coordinates.get(0));
        hash = 29 * hash + Objects.hashCode(this.coordinates.get(1));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }
        final GeoPoint other = (GeoPoint) obj;

        return Objects.equals(this.coordinates.get(0), other.coordinates.get(0))
            && Objects.equals(this.coordinates.get(1), other.coordinates.get(1));
    }

    @Override
    public String toString() {
        return "GeoPoint{" +
            "longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            '}';
    }
}
