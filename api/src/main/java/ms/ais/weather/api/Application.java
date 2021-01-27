package ms.ais.weather.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import io.javalin.plugin.json.JavalinJackson;
import ms.ais.weather.api.controller.ForecastController;
import ms.ais.weather.api.enums.ServerConfig;

public class Application {

    public static void main(String[] args) {
        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins)
            .start(ServerConfig.INSTANCE.getPort());

        final String baseURL = "/ms/ais/api/forecast";

        app.get(baseURL, ctx -> ctx.result("Server Is Up and Running..."));
        app.get(baseURL + "/current/city/:city", ForecastController.getCurrentWeatherForecastResponse);
        app.get(baseURL + "/daily/city/:city", ForecastController.getDailyWeatherForecastResponse);
        app.get(baseURL + "/hourly/city/:city", ForecastController.getHourlyWeatherForecastResponse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavalinJackson.configure(mapper);
    }

}
