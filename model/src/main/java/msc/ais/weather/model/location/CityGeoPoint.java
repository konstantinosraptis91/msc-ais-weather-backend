package msc.ais.weather.model.location;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public class CityGeoPoint extends GeoPoint {

    /**
     * The city name
     */
    private String cityName;

    private CityGeoPoint(Builder builder) {
        super(builder.longitude, builder.latitude);
        this.cityName = builder.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String cityName;
        private double longitude;
        private double latitude;

        public Builder withCityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public Builder withLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public CityGeoPoint build() {
            return new CityGeoPoint(this);
        }

    }

    @Override
    public String toString() {
        return "CityGeoPoint{" +
            "cityName='" + cityName + "'" +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            '}';
    }

}
