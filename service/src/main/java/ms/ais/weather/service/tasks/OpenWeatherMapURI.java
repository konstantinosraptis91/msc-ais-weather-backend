package ms.ais.weather.service.tasks;

import ms.ais.weather.model.enums.WeatherForecastType;
import ms.ais.weather.model.location.CityGeoPoint;
import ms.ais.weather.service.enums.CityGeoPointMap;
import ms.ais.weather.service.enums.UnitsType;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public class OpenWeatherMapURI {

    private static final Logger LOGGER = Logger.getLogger(OpenWeatherMapURI.class.getName());

    private final CityGeoPoint cityGeoPoint;

    private final String key;
    private final UnitsType unitsType;
    private final WeatherForecastType weatherForecastType;

    private URI uri;

    private OpenWeatherMapURI(Builder builder) {

        this.cityGeoPoint = CityGeoPointMap.INSTANCE.getCityGeoPoint(builder.cityName).get();
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
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
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
                uriBuilder.setParameter("q", cityGeoPoint.getCityName())
                    .setParameter("appid", key)
                    .setParameter("units", unitsType.getValue());
                break;

            case DAILY:
                uriBuilder.setParameter("lon", String.valueOf(cityGeoPoint.getLongitude()))
                    .setParameter("lat", String.valueOf(cityGeoPoint.getLatitude()))
                    .setParameter("exclude", "current,minutely,hourly,alerts")
                    .setParameter("appid", key)
                    .setParameter("units", unitsType.getValue());
                break;

            case HOURLY:
                uriBuilder.setParameter("lon", String.valueOf(cityGeoPoint.getLongitude()))
                    .setParameter("lat", String.valueOf(cityGeoPoint.getLatitude()))
                    .setParameter("exclude", "current,minutely,daily,alerts")
                    .setParameter("appid", key)
                    .setParameter("units", unitsType.getValue());
                break;

        }

    }

    public CityGeoPoint getCityGeoPoint() {
        return cityGeoPoint;
    }

    public URI getURI() {
        return uri;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String cityName;
        private String key;
        private UnitsType unitsType;
        private WeatherForecastType weatherForecastType;

        public Builder withCityName(String cityName) {
            this.cityName = cityName;
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
