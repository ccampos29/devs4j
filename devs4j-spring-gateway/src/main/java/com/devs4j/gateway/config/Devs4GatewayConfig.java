package com.devs4j.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class Devs4GatewayConfig {

    @Autowired
    private Devs4jAuthFilter devs4jAuthFilter;

//    @Bean
//    @Profile("localhostRouter-noEureka")
//    public RouteLocator configLocalNoEureka(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/api/v1/dragonball/*").uri("http://localhost:8081"))
//                .route(r -> r.path("/api/v1/gameofthrones/*").uri("http://localhost:8083"))
//                .build();
//    }
//
//    @Bean
//    @Profile("localhostRouter-eureka")
//    public RouteLocator configLocalEureka(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/api/v1/dragonball/*").uri("lb://devs4j-dragon-ball"))
//                .route(r -> r.path("/api/v1/gameofthrones/*").uri("lb://devs4j-game-of-thrones"))
//                .build();
//    }

    @Bean
    @Profile("localhostRouter-eureka-cb")
    public RouteLocator configLocalEurekaCB(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/dragonball/*")
                        .filters(f -> {
                            f.circuitBreaker(c -> c.setName("failoverCB")
                                    .setFallbackUri("forward:/api/v1/db-failover/dragonball/characters")
                                    .setRouteId("dbFailover"));
                            f.filter(devs4jAuthFilter);
                            return f;
                        })
                        .uri("lb://devs4j-dragon-ball"))
                .route(r -> r.path("/api/v1/gameofthrones/*")
                        .filters(f -> f.filter(devs4jAuthFilter))
                        .uri("lb://devs4j-game-of-thrones")
                )
                .route(r -> r.path("/api/v1/db-failover/dragonball/characters").uri("lb://devs4j-dragon-ball-failover"))
                .route(r -> r.path("/auth/**").uri("lb://devs4j-auth"))
                .build();
    }
}
