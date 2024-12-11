package org.example.users.service;

import lombok.RequiredArgsConstructor;
import org.example.users.dto.User;
import org.example.users.dto.UserWithOrders;
import org.example.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserCacheService userCacheService;
    private final OrderClient orderClient;

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<User> getUserWithCache(Long id) {
        return userCacheService.getUserFromCache(id)
                .switchIfEmpty(
                        userRepository.findById(id)
                                .flatMap(user -> userCacheService.saveUserToCache(user).thenReturn(user))
                );
    }

    public Mono<UserWithOrders> getUserWithOrders(Long userId) {
        return userRepository.findById(userId)
                .flatMap(user -> orderClient.getOrdersByUserId(userId)
                        .collectList()
                        .map(orders -> new UserWithOrders(user, orders)));
    }
}

