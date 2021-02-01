package ms.ais.weather.service;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 1/2/2021.
 */
public interface UserCityService {

    boolean deleteUserCityByTokenId(String tokenId, int cityId);

    boolean insertUserCityByTokenId(String tokenId, int cityId);
}
