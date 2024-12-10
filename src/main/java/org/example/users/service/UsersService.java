package org.example.users.service;

import org.example.users.dto.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UsersService {
    public Mono<User> register(User user) {
        return Mono.empty();
    }
}
