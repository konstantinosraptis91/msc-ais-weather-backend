package ms.ais.weather.service;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 21/1/2021.
 */
public class ServiceFactory {

    public static WeatherService createWeatherService() {
        return new OpenWeatherMapService();
    }

    public static UserService createUserService() {
        return new UserServiceImpl();
    }

}
