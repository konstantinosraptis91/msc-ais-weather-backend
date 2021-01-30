package ms.ais.weather.model.db;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class UserCity {

    private int userId;
    private int cityId;

    private UserCity(int userId, int cityId) {
        this.userId = userId;
        this.cityId = cityId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCityId() {
        return cityId;
    }

    public static UserCity of(int userId, int cityId) {
        return new UserCity(userId, cityId);
    }

    @Override
    public String toString() {
        return "UserCity{" +
            "userId=" + userId +
            ", cityId=" + cityId +
            '}';
    }
}
