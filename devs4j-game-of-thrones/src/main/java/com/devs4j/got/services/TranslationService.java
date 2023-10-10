package com.devs4j.got.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TranslationService {

    private static final Logger log = LoggerFactory.getLogger(TranslationService.class);
    public Map<String, String> words = new HashMap<>();

    @PostConstruct
    public void init() {
        words.put("Hello", "Hola");
        words.put("Bye", "Adios");
        words.put("Word", "Palabra");
    }

    @Cacheable("translations")
    public Optional<String> getTranslation(String message) {
        log.info("Doing translation for {}", message);

        for(String word : words.keySet()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(word.equals(message)) {
                return Optional.of(words.get(message));
            }
        }
        return Optional.empty();
    }

    @CacheEvict("translations")
    public void clearCache(String message) {
    }
}
