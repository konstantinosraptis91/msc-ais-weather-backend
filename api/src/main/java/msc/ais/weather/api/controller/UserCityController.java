package msc.ais.weather.api.controller;

import io.javalin.http.Handler;
import msc.ais.weather.service.ServiceFactory;
import org.eclipse.jetty.http.HttpStatus;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 1/2/2021.
 */
public class UserCityController {

    public static Handler deleteUserCityByTokenId =
        ctx -> {
            boolean isDeleted = ServiceFactory.createUserCityService()
                .deleteUserCityByTokenId(
                    ctx.queryParam("tokenId"),
                    ctx.queryParam("cityId", Integer.class).get());

            if (isDeleted) {
                ctx.status(HttpStatus.OK_200);
            } else {
                ctx.status(HttpStatus.NOT_FOUND_404);
            }
        };

    public static Handler insertUserCityByTokenId =
        ctx -> {
            boolean isInserted = ServiceFactory.createUserCityService()
                .insertUserCityByTokenId(
                    ctx.queryParam("tokenId"),
                    ctx.queryParam("cityId", Integer.class).get());

            if (isInserted) {
                ctx.status(HttpStatus.OK_200);
            } else {
                ctx.status(HttpStatus.NOT_FOUND_404);
            }
        };

}
