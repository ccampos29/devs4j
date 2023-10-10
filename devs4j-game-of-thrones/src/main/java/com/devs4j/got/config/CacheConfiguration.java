package com.devs4j.got.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class CacheConfiguration {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }

    @Bean
    public CacheManager getManager(RedissonClient redisson) {
        Map<String, CacheConfig> configMap = new HashMap<>();
        configMap.put("translations", new CacheConfig());
        return new RedissonSpringCacheManager(redisson);
    }

//    @Bean
//    public CacheManager getManager() {
//        return new ConcurrentMapCacheManager("translations");
//    }
}
