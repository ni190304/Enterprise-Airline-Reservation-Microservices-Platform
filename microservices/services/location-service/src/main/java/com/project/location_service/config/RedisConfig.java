
package com.project.location_service.config;

import java.time.Duration;
import java.util.Map;

import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig implements CachingConfigurer {

    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory connectionFactory) {

        PolymorphicTypeValidator typeValidator =
        BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Object.class)
                .build();

GenericJacksonJsonRedisSerializer serializer =
        GenericJacksonJsonRedisSerializer.builder()
                .enableDefaultTyping(typeValidator)
                .build();

        RedisCacheConfiguration defaults =
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(
                                                new StringRedisSerializer()))
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(serializer))
                        .disableCachingNullValues();

        Map<String, RedisCacheConfiguration> cacheConfigs = Map.of(
                "airports", defaults.entryTtl(Duration.ofHours(6)),
                "airportsByCity", defaults.entryTtl(Duration.ofHours(6)),
                "allAirports", defaults.entryTtl(Duration.ofHours(6)),
                "cities", defaults.entryTtl(Duration.ofHours(6)),
                "citiesByCode", defaults.entryTtl(Duration.ofHours(6))
        );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaults.entryTtl(Duration.ofHours(6)))
                .withInitialCacheConfigurations(cacheConfigs)
                .build();
    }
}
