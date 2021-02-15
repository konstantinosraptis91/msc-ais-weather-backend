package msc.ais.weather.api.controller;

import io.javalin.http.Handler;
import msc.ais.weather.model.db.User;
import msc.ais.weather.service.ServiceFactory;
import org.eclipse.jetty.http.HttpStatus;

import java.util.Optional;


/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 31/1/2021.
 */
public class UserController {

    public static Handler signUpUser =
        ctx -> {
            UserRequest request = ctx.bodyValidator(UserRequest.class).get();
            ServiceFactory.createUserService()
                .signUp(User.builder()
                    .firstName(request.firstName)
                    .lastName(request.lastName)
                    .email(request.email)
                    .password(request.password.toCharArray())
                    .build())
                .ifPresentOrElse(tokenId -> {
                    ctx.json(tokenId);
                    ctx.status(HttpStatus.CREATED_201);
                }, () -> ctx.status(HttpStatus.BAD_REQUEST_400));
        };

    public static Handler signInUser =
        ctx -> ServiceFactory.createUserService()
            .singIn(
                ctx.queryParam("email"),
                Optional.ofNullable(ctx.queryParam("password"))
                    .orElseThrow(() -> new IllegalArgumentException("Unable to find password query param"))
                    .toCharArray())
            .ifPresentOrElse(tokenId -> {
                ctx.json(tokenId);
                ctx.status(HttpStatus.OK_200);
            }, () -> ctx.status(HttpStatus.NOT_FOUND_404));

    public static Handler signOutUser =
        ctx -> {
            boolean isSingedOut = ServiceFactory.createUserService()
                .signOut(ctx.queryParam("tokenId"));
            if (isSingedOut) {
                ctx.status(HttpStatus.OK_200);
            } else {
                ctx.status(HttpStatus.NOT_FOUND_404);
            }
        };

    static class UserRequest {
        public String firstName;
        public String lastName;
        public String email;
        public String password;
    }
}
