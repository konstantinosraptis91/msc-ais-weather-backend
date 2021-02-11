package msc.ais.weather.model.location;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
@JsonPropertyOrder({
    "cityGeoPoint", "id", "country"
})
public class City {

    private final CityGeoPoint cityGeoPoint;
    private String country;

    private City(Builder builder) {
        this.cityGeoPoint = builder.cityGeoPoint;
        this.country = builder.country;
    }

    public CityGeoPoint getCityGeoPoint() {
        return cityGeoPoint;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private CityGeoPoint cityGeoPoint;
        private String country;

        public Builder cityGeoPoint(CityGeoPoint cityGeoPoint) {
            this.cityGeoPoint = cityGeoPoint;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public City build() {
            return new City(this);
        }

    }

    @Override
    public String toString() {
        return "City{" +
            "cityGeoPoint=" + cityGeoPoint +
            ", country='" + country + '\'' +
            '}';
    }
}
