package ms.ais.weather.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ms.ais.weather.model.HourlyWeatherForecast;
import ms.ais.weather.model.location.CityGeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 22/1/2021.
 */
public class HourlyWeatherForecastResponse {

    private final CityGeoPoint cityGeoPoint;
    private List<HourlyWeatherForecast> forecastList;

    private HourlyWeatherForecastResponse(CityGeoPoint cityGeoPoint) {
        this.cityGeoPoint = cityGeoPoint;
    }

    public static HourlyWeatherForecastResponse createInstance(CityGeoPoint cityGeoPoint) {
        return new HourlyWeatherForecastResponse(cityGeoPoint);
    }

    public CityGeoPoint getCityGeoPoint() {
        return cityGeoPoint;
    }

    @JsonProperty("hourly")
    public List<HourlyWeatherForecast> getForecastList() {
        if (Objects.isNull(forecastList)) {
            forecastList = new ArrayList<>();
        }
        return forecastList;
    }
}
