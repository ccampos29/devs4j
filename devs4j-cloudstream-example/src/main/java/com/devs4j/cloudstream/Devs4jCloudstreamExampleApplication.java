package com.devs4j.cloudstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class Devs4jCloudstreamExampleApplication {

    private static final Logger log = LoggerFactory.getLogger(Devs4jCloudstreamExampleApplication.class);

    @Bean
    public Function<String, String> toUpperCase() {
        return data -> {
            log.info("Data {}", data);
            return data.toUpperCase();
        };
        //return String::toUpperCase;
    }

    //@Bean
    public Supplier<Flux<Long>> producer() {
        return () -> Flux.interval(Duration.ofSeconds(1)).log();
    }

    //@Bean
    public Function<Flux<Long>, Flux<Long>> processor() {
        return flx -> flx.map(n -> n * n);
    }

	//@Bean
	public Consumer<Long> consumer() {
		return (n) -> log.info("Value {}", n);
	}

    public static void main(String[] args) {
        SpringApplication.run(Devs4jCloudstreamExampleApplication.class, args);
    }

}
