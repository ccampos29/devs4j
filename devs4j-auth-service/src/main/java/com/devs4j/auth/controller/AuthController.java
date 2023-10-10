package com.devs4j.auth.controller;

import com.devs4j.auth.dto.TokenDTO;
import com.devs4j.auth.dto.UserDTO;
import com.devs4j.auth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthUserService authUserService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authUserService.login(userDTO));
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDTO> validate(@RequestParam String token) {
        return ResponseEntity.ok(authUserService.validate(token));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authUserService.save(userDTO));
    }

}
