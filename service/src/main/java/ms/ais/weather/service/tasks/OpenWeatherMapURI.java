package ms.ais.weather.service.tasks;

import ms.ais.weather.model.enums.WeatherForecastType;
import ms.ais.weather.service.enums.UnitsType;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public class OpenWeatherMapURI {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherMapURI.class);

    private final double longitude;
    private final double latitude;
    private final String cityName;
    private final String key;
    private final UnitsType unitsType;
    private final WeatherForecastType weatherForecastType;

    private URI uri;

    private OpenWeatherMapURI(Builder builder) {
        this.cityName = builder.cityName;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.key = builder.key;
        this.unitsType = builder.unitsType;
        this.weatherForecastType = builder.weatherForecastType;

        URIBuilder uriBuilder = new URIBuilder()
            .setScheme("https")
            .setHost("api.openweathermap.org/data/2.5");
        applyPath(uriBuilder);
        applyParameters(uriBuilder);

        try {
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void applyPath(URIBuilder uriBuilder) {

        switch (weatherForecastType) {

            case CURRENT:
                uriBuilder.setPath("/weather");
                break;

            case DAILY:
            case HOURLY:
                uriBuilder.setPath("/onecall");
                break;
        }

    }

    private void applyParameters(URIBuilder uriBuilder) {

        switch (weatherForecastType) {

            case CURRENT:
                uriBuilder.setParameter("q", cityName)
                    .setParameter("appid", key)
                    .setParameter("units", unitsType.getValue());
                break;

            case DAILY:
                uriBuilder.setParameter("lon", String.valueOf(longitude))
                    .setParameter("lat", String.valueOf(latitude))
                    .setParameter("exclude", "current,minutely,hourly,alerts")
                    .setParameter("appid", key)
                    .setParameter("units", unitsType.getValue());
                break;

            case HOURLY:
                uriBuilder.setParameter("lon", String.valueOf(longitude))
                    .setParameter("lat", String.valueOf(latitude))
                    .setParameter("exclude", "current,minutely,daily,alerts")
                    .setParameter("appid", key)
                    .setParameter("units", unitsType.getValue());
                break;

        }

    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getCityName() {
        return cityName;
    }

    public String getKey() {
        return key;
    }

    public UnitsType getUnitsType() {
        return unitsType;
    }

    public WeatherForecastType getWeatherForecastType() {
        return weatherForecastType;
    }

    public URI getURI() {
        return uri;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String cityName;
        private double longitude;
        private double latitude;
        private String key;
        private UnitsType unitsType;
        private WeatherForecastType weatherForecastType;

        public Builder withCityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withKey(String key) {
            this.key = key;
            return this;
        }

        public Builder withUnitsType(UnitsType unitsType) {
            this.unitsType = unitsType;
            return this;
        }

        public Builder withWeatherForecastType(WeatherForecastType weatherForecastType) {
            this.weatherForecastType = weatherForecastType;
            return this;
        }

        public OpenWeatherMapURI build() {
            return new OpenWeatherMapURI(this);
        }

    }

}
