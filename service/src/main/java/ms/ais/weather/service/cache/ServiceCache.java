package ms.ais.weather.service.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.time.Duration;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 2/2/2021.
 */
public enum ServiceCache {

    OPEN_WEATHER_MAP_CACHE("OpenWeatherMapCache");

    private final Cache<String, String> cache;

    ServiceCache(String cacheName) {
        CacheManager cacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            .withCache(cacheName,
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                    String.class, String.class, ResourcePoolsBuilder.heap(1000))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofHours(1)))
            )
            .build();
        cacheManager.init();
        cache = cacheManager.getCache(cacheName, String.class, String.class);
    }

    public Cache<String, String> getCache() {
        return cache;
    }
}
