package ms.ais.weather.db.sqlite;

import ms.ais.weather.db.CityDao;
import ms.ais.weather.db.DaoFactory;
import ms.ais.weather.model.db.City;
import ms.ais.weather.model.location.CityGeoPoint;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class SqliteCityDaoTest {

    @Disabled
    @Test
    public void testInsertCity() throws Exception {

//        City city = City.builder()
//            .cityGeoPoint(CityGeoPoint.builder()
//                .withCityName("Athens")
//                .withLongitude(23.7162)
//                .withLatitude(37.9795)
//                .build())
//            .country("Greece")
//            .build();

        City city = City.builder()
            .cityGeoPoint(CityGeoPoint.builder()
                .withCityName("Rome")
                .withLongitude(-85.1647)
                .withLatitude(34.257)
                .build())
            .country("Italy")
            .build();

        CityDao dao = DaoFactory.createCityDao();
        System.out.println("Insert city: " + dao.insertCity(city));
    }

    @Disabled
    @Test
    public void testFindByUserId() throws Exception {

        CityDao dao = DaoFactory.createCityDao();
        dao.findByUserId(1).forEach(System.out::println);
    }

    @Disabled
    @Test
    public void testFindByCityName() throws Exception {

        CityDao dao = DaoFactory.createCityDao();
        dao.findByCityName("Athens")
            .ifPresent(System.out::println);
    }

    @Test
    public void testFindByTokenId() throws Exception {

        CityDao dao = DaoFactory.createCityDao();
        dao.findByUserTokenId("d4663bfc-ad58-4691-80a9-0f3da69d87ee")
            .forEach(System.out::println);
    }

}
