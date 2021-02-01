package ms.ais.weather.service.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 1/2/2021.
 */
public class CacheHelper {

    private CacheManager cacheManager;
    private Cache<String, String> openWeatherMapCache;

    public CacheHelper() {
        cacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            .withCache("preConfigured",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                    String.class, String.class, ResourcePoolsBuilder.heap(10)))
            .build();
        cacheManager.init();

        openWeatherMapCache = cacheManager.getCache("preConfigured", String.class, String.class);

    }

}
