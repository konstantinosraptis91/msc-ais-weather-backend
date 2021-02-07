package ms.ais.weather.api.controller;

import io.javalin.http.Handler;
import ms.ais.weather.service.ServiceFactory;
import org.eclipse.jetty.http.HttpStatus;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 21/1/2021.
 */
public class ForecastController {

    public static Handler getCurrentWeatherForecastResponseByIP =
        ctx ->
            ServiceFactory.createWeatherService()
                .getCurrentWeatherForecastResponseByIP(ctx.header("x-forwarded-for"))
                .ifPresentOrElse(ctx::json,
                    () -> ctx.status(HttpStatus.NOT_FOUND_404));

    public static Handler getHourlyWeatherForecastResponseByIP =
        ctx ->
            ServiceFactory.createWeatherService()
                .getHourlyWeatherForecastResponseByIP(ctx.header("x-forwarded-for"))
                .ifPresentOrElse(ctx::json,
                    () -> ctx.status(HttpStatus.NOT_FOUND_404));

    public static Handler getDailyWeatherForecastResponseByIP =
        ctx ->
            ServiceFactory.createWeatherService()
                .getDailyWeatherForecastResponseByIP(ctx.header("x-forwarded-for"))
                .ifPresentOrElse(ctx::json,
                    () -> ctx.status(HttpStatus.NOT_FOUND_404));

    public static Handler getCurrentWeatherForecastResponseByCity =
        ctx ->
            ServiceFactory.createWeatherService()
                .getCurrentWeatherForecastResponse(
                    ctx.pathParam("city"))
                .ifPresentOrElse(ctx::json,
                    () -> ctx.status(HttpStatus.NOT_FOUND_404));

    public static Handler getHourlyWeatherForecastResponseByCity =
        ctx ->
            ServiceFactory.createWeatherService()
                .getHourlyWeatherForecastResponse(
                    ctx.pathParam("city"))
                .ifPresentOrElse(ctx::json,
                    () -> ctx.status(HttpStatus.NOT_FOUND_404));

    public static Handler getDailyWeatherForecastResponseByCity =
        ctx ->
            ServiceFactory.createWeatherService()
                .getDailyWeatherForecastResponse(
                    ctx.pathParam("city"))
                .ifPresentOrElse(ctx::json,
                    () -> ctx.status(HttpStatus.NOT_FOUND_404));

}
