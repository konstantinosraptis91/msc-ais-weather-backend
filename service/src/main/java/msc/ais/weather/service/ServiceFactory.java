package msc.ais.weather.service;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 21/1/2021.
 */
public class ServiceFactory {

    public static WeatherService createOpenWeatherMapWeatherService(ServiceOptions options) {
        return new OpenWeatherMapService(options);
    }

    public static GeocodingService createOpenWeatherMapGeocodingService(ServiceOptions options) {
        return new OpenWeatherMapService(options);
    }

}
