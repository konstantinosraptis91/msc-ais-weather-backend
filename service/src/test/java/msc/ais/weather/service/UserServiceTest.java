package msc.ais.weather.service;

import msc.ais.weather.model.db.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 31/1/2021.
 */
public class UserServiceTest {

    @Disabled
    @Test
    public void testSignUp() throws Exception {

        UserService service = ServiceFactory.createUserService();
        service.signUp(User.builder()
            .firstName("Konstantinos")
            .lastName("Raptis")
            .email("kraptis@test.com")
            .password(new char[]{'a', 'b', 'c'})
            .build())
            .ifPresentOrElse(System.out::println, SQLException::new);
    }

    @Disabled
    @Test
    public void testSignIn() throws Exception {

        UserService service = ServiceFactory.createUserService();
        service.singIn("kraptis@test.com", new char[]{'a', 'b', 'c'})
            .ifPresentOrElse(System.out::println, SQLException::new);
    }

}
