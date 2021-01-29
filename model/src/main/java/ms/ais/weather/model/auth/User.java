package ms.ais.weather.model.auth;

import java.util.Arrays;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class User {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final char[] password;

    private User(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public char[] getPassword() {
        return password;
    }

    public interface UserId {
        UserFirstName userId(int id);
    }

    public interface UserFirstName {
        UserLastName firstName(String firstName);
    }

    public interface UserLastName {
        UserEmail lastName(String lastName);
    }

    public interface UserEmail {
        UserPassword email(String email);
    }

    public interface UserPassword {
        UserBuild password(char[] password);
    }

    public interface UserBuild {
        User build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
        implements UserId, UserFirstName, UserLastName, UserEmail, UserPassword, UserBuild {

        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private char[] password;

        @Override
        public UserFirstName userId(int id) {
            this.id = id;
            return this;
        }

        @Override
        public UserLastName firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        @Override
        public UserEmail lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        @Override
        public UserPassword email(String email) {
            this.email = email;
            return this;
        }

        @Override
        public UserBuild password(char[] password) {
            this.password = password;
            return this;
        }

        @Override
        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", password=" + Arrays.toString(password) +
            '}';
    }
}
