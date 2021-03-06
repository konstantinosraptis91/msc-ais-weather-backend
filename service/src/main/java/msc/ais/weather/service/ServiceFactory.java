package msc.ais.weather.service;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 21/1/2021.
 */
public class ServiceFactory {

    public static WeatherService createWeatherService() {
        return new OpenWeatherMapService();
    }

    public static GeocodingService createGeocodingService() {
        return new OpenWeatherMapService();
    }

    public static UserService createUserService() {
        return new UserServiceImpl();
    }

    public static CityService createCityService() {
        return new CityServiceImpl();
    }

    public static UserCityService createUserCityService() {
        return new UserCityServiceImpl();
    }

}
