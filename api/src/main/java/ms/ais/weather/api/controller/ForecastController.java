package ms.ais.weather.api.controller;

import io.javalin.http.Handler;
import ms.ais.weather.retriever.RetrieverFactory;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 21/1/2021.
 */
public class ForecastController {

    public static Handler getCurrentWeatherForecast =
        ctx ->
            ctx.json(
                RetrieverFactory.createWeatherDataRetriever()
                    .getCurrentWeatherForecast(
                        ctx.pathParam("city")
                    ));

}
