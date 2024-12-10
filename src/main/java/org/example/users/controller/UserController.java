package org.example.users.controller;

import lombok.RequiredArgsConstructor;
import org.example.users.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import org.example.users.service.UsersService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody User user) {
        return usersService.register(user)
                .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body("User registered"))
                .onErrorReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed"));
    }
}
