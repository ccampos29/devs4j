package com.devs4j.got.controller;

import com.devs4j.got.services.TranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/translations")
public class MessageController {

    @Autowired
    private TranslationService translationService;
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @GetMapping
    public ResponseEntity<String> getTranslation(@RequestParam("message") String message) {
        log.info("Message receipt {}", message);

        Optional<String> translation = translationService.getTranslation(message);

        return translation.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping
    public void clearCache(@RequestParam("message") String message) {
        log.info("Cleaning cache for {}", message);
        translationService.clearCache(message);
    }
}
