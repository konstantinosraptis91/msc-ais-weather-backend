package msc.ais.weather.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import io.javalin.plugin.json.JavalinJackson;
import msc.ais.weather.api.controller.CityController;
import msc.ais.weather.api.controller.ForecastController;
import msc.ais.weather.api.controller.UserCityController;
import msc.ais.weather.api.controller.UserController;
import msc.ais.weather.api.conf.ServerConfig;

public class Application {

    public static void main(String[] args) {
        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins)
            .start(ServerConfig.getPort());

        final String baseURL = "/msc/ais/weather/api/";

        app.get(baseURL, ctx -> ctx.result("Server Is Up and Running..."));
        app.get(baseURL + "/forecast/current", ForecastController.getCurrentWeatherForecastResponseByIP);
        app.get(baseURL + "/forecast/daily", ForecastController.getDailyWeatherForecastResponseByIP);
        app.get(baseURL + "/forecast/hourly", ForecastController.getHourlyWeatherForecastResponseByIP);

        app.get(baseURL + "/forecast/current/city/:city", ForecastController.getCurrentWeatherForecastResponseByCity);
        app.get(baseURL + "/forecast/daily/city/:city", ForecastController.getDailyWeatherForecastResponseByCity);
        app.get(baseURL + "/forecast/hourly/city/:city", ForecastController.getHourlyWeatherForecastResponseByCity);

        app.post(baseURL + "/user/signup", UserController.signUpUser);
        app.get(baseURL + "/user/signin", UserController.signInUser);
        app.delete(baseURL + "/user/signout", UserController.signOutUser);

        app.get(baseURL + "/user/cities", CityController.getCitiesByUserTokenId);
        app.delete(baseURL + "/user/city", UserCityController.deleteUserCityByTokenId);
        app.post(baseURL + "/user/city", UserCityController.insertUserCityByTokenId);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavalinJackson.configure(mapper);
    }

}
