package com.devs4j.rest.controller;

import com.devs4j.rest.error.CharacterNotFound;
import com.github.javafaker.Faker;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/characters")
@RestController
public class CharacterController {

    private Faker faker = new Faker();
    private List<String> characters = new ArrayList<>();

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            characters.add(faker.dragonBall().character());
        }
    }

    @RequestMapping(value = "/dragonBall", method = RequestMethod.GET)
    public List<String> getCharacters() {
        return characters;
    }

    @RequestMapping(value = "/dragonBall/{name}", method = RequestMethod.GET)
    public String getCharacterByName(@PathVariable("name") String name) {
        return characters
                .stream()
                .filter(
                        c -> c.equals(name)
                )
                .findAny()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }

    @RequestMapping(value = "/dragonBall/search", method = RequestMethod.GET)
    public List<String> getCharactersByPrefix(@RequestParam("prefix") String prefix) {
        List<String> result = characters
                .stream()
                .filter(
                        c -> c.startsWith(prefix)
                )
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new CharacterNotFound();
        }
        return result;
    }
}