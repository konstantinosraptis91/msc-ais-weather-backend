package ms.ais.weather.model.auth;

import ms.ais.weather.model.location.CityGeoPoint;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class City {

    private final int id;
    private final CityGeoPoint cityGeoPoint;
    private final String country;

    private City(Builder builder) {
        this.id = builder.id;
        this.cityGeoPoint = builder.cityGeoPoint;
        this.country = builder.country;
    }

    public int getId() {
        return id;
    }

    public CityGeoPoint getCityGeoPoint() {
        return cityGeoPoint;
    }

    public String getCountry() {
        return country;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int id;
        private CityGeoPoint cityGeoPoint;
        private String country;

        public Builder cityId(int id) {
            this.id = id;
            return this;
        }

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
            "id=" + id +
            ", cityGeoPoint=" + cityGeoPoint +
            ", country='" + country + '\'' +
            '}';
    }
}
