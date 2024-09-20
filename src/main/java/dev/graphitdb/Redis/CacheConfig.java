package dev.graphitdb.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

/**
 * Configuration class for setting up caching with Redis in a Spring application.
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    @Value("${spring.cache.redis.time-to-live}")
    private Duration timeToLive;

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * Configures the CacheManager to use Redis as the caching provider.
     *
     * @return CacheManager configured to manage caches using Redis.
     */
    @Override
    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues() // Do not cache null values
                .entryTtl(timeToLive); // Set the time-to-live for cache entries

        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(redisCacheConfiguration) // Apply the default cache configuration
                .transactionAware() // Enable transaction awareness
                .build();
    }
}
