package msc.ais.weather.api.controller;

import io.javalin.http.Handler;
import msc.ais.weather.service.ServiceFactory;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 1/2/2021.
 */
public class CityController {

    public static Handler getCitiesByUserTokenId =
        ctx -> ctx.json(ServiceFactory.createCityService()
            .findCitiesByTokenId(ctx.queryParam("tokenId")));

}
