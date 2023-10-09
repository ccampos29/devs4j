package com.devs4j.client.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Devs4jLoadBalancerConfiguration {

    private final static Logger log = LoggerFactory.getLogger(Devs4jLoadBalancerConfiguration.class);

    @Bean
    public ServiceInstanceListSupplier instanceSupplier(ConfigurableApplicationContext context) {
        log.info("Configuring load balancer to prefer same instance");
        return ServiceInstanceListSupplier.builder()
                .withBlockingDiscoveryClient()
                .build(context);
    }

}
