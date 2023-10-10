package com.devs4j.auth.service;

import com.devs4j.auth.config.JwtProvider;
import com.devs4j.auth.dto.TokenDTO;
import com.devs4j.auth.dto.UserDTO;
import com.devs4j.auth.jpa.entity.UserEntity;
import com.devs4j.auth.jpa.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private JwtProvider jwtProvider;

    public UserDTO save(UserDTO userDTO) {
        Optional<UserEntity> user = userRepository.findByUsername(userDTO.getUsername());

        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("User %s already exists", userDTO.getUsername()));
        }

        UserEntity newUser = userRepository.save(new UserEntity(userDTO.getUsername(), encoder.encode(userDTO.getPassword())));

        return mapper.map(newUser, UserDTO.class);
    }

    public TokenDTO login(UserDTO user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED)
        );

        if (encoder.matches(user.getPassword(), userEntity.getPassword())) {
            return new TokenDTO(jwtProvider.createToken(userEntity));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public TokenDTO validate(String token) {
        jwtProvider.validate(token);
        String username = jwtProvider.getUsernameFromToken(token);
        userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED)
                );

        return new TokenDTO(token);
    }
}
