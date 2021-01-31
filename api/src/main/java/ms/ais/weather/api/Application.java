package ms.ais.weather.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import io.javalin.plugin.json.JavalinJackson;
import ms.ais.weather.api.controller.ForecastController;
import ms.ais.weather.api.controller.UserController;
import ms.ais.weather.api.enums.ServerConfig;

public class Application {

    public static void main(String[] args) {

        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins)
            .start(ServerConfig.INSTANCE.getPort());

        final String baseURL = "/ms/ais/api";

        app.get(baseURL, ctx -> ctx.result("Server Is Up and Running..."));
        app.get(baseURL + "/forecast/current/city/:city", ForecastController.getCurrentWeatherForecastResponse);
        app.get(baseURL + "/forecast/daily/city/:city", ForecastController.getDailyWeatherForecastResponse);
        app.get(baseURL + "/forecast/hourly/city/:city", ForecastController.getHourlyWeatherForecastResponse);

        app.post(baseURL + "/user/signup", UserController.signUpUser);
        app.get(baseURL + "/user/signin", UserController.signInUser);
        app.delete(baseURL + "/user/signout", UserController.signOutUser);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavalinJackson.configure(mapper);
    }

}
