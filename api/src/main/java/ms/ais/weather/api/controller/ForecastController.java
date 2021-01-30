package ms.ais.weather.api.controller;

import io.javalin.http.Handler;
import ms.ais.weather.service.ServiceFactory;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 21/1/2021.
 */
public class ForecastController {

    public static Handler getCurrentWeatherForecastResponse =
        ctx ->
            ctx.json(
                ServiceFactory.createWeatherService()
                    .getCurrentWeatherForecastResponse(
                        ctx.pathParam("city")
                    ));

    public static Handler getHourlyWeatherForecastResponse =
        ctx ->
            ctx.json(
                ServiceFactory.createWeatherService()
                    .getHourlyWeatherForecastResponse(
                        ctx.pathParam("city")
                    ));

    public static Handler getDailyWeatherForecastResponse =
        ctx ->
            ctx.json(
                ServiceFactory.createWeatherService()
                    .getDailyWeatherForecastResponse(
                        ctx.pathParam("city")
                    ));

}
