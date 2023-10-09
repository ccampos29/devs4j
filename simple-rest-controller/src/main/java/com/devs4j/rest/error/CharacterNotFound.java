package com.devs4j.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Character not found")
public class CharacterNotFound extends RuntimeException {

    private static final long serialVersionUID = 5200316585204942998L;

}
