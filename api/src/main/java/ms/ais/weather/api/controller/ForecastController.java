package ms.ais.weather.api.controller;

import io.javalin.http.Handler;
import ms.ais.weather.retriever.RetrieverFactory;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 21/1/2021.
 */
public class ForecastController {

    public static Handler getCurrentWeatherForecastResponse =
        ctx ->
            ctx.json(
                RetrieverFactory.createWeatherDataRetriever()
                    .getCurrentWeatherForecastResponse(
                        ctx.pathParam("city")
                    ));

    public static Handler getHourlyWeatherForecastResponse =
        ctx ->
            ctx.json(
                RetrieverFactory.createWeatherDataRetriever()
                    .getHourlyWeatherForecastResponse(
                        ctx.pathParam("city")
                    ));

    public static Handler getDailyWeatherForecastResponse =
        ctx ->
            ctx.json(
                RetrieverFactory.createWeatherDataRetriever()
                    .getDailyWeatherForecastResponse(
                        ctx.pathParam("city")
                    ));

}
