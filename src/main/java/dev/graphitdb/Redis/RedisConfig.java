package dev.graphitdb.Redis;

import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Configuration class for setting up Lettuce-based Redis connections in a Spring application.
 */
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.password:}")
    private String redisPassword;

    @Value("${spring.redis.lettuce.timeout}")
    private long commandTimeoutMillis;

    /**
     * Creates a LettuceConnectionFactory with configured Redis connection settings for a Redis cluster.
     *
     * @return LettuceConnectionFactory configured for Redis cluster settings.
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration()
                .clusterNode(redisHost, redisPort);

        if (!redisPassword.isEmpty()) {
            redisClusterConfiguration.setPassword(redisPassword);
        }

        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(commandTimeoutMillis))
                .clientResources(clientResources())
                .build();

        return new LettuceConnectionFactory(redisClusterConfiguration, clientConfiguration);
    }

    /**
     * Configures a RedisTemplate for interacting with Redis data.
     *
     * @return RedisTemplate<String, Object> configured for Redis operations.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());

        // Use StringRedisSerializer for keys
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Use JSON Serializer for values
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    /**
     * Provides client resources for Lettuce connections.
     *
     * @return DefaultClientResources instance for managing Lettuce client resources.
     */
    @Bean
    public io.lettuce.core.resource.ClientResources clientResources() {
        return DefaultClientResources.create();
    }
}
