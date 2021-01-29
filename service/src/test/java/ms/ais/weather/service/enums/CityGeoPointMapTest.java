package ms.ais.weather.service.enums;

import org.junit.jupiter.api.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public class CityGeoPointMapTest {

    @Test
    public void testGetCityGeoPointList() {
        CityGeoPointMap.INSTANCE.getCityGeoPointMap()
            .forEach((key, value) -> System.out.println("key: " + key + ", value: " + value));
    }

}
