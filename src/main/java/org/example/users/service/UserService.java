package org.example.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.users.dto.User;
import org.example.users.dto.UserWithOrders;
import org.example.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
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

    public Flux<Object> getUserWithOrders(Long userId) {
        log.info("Fetching user with orders for userId = {}", userId);
        return userRepository.findById(userId)
                .doOnNext(user -> log.info("User found: {}", user))
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMapMany(user -> {
                    log.info("Fetching orders for userId: {}", userId);
                    return orderClient.getOrdersByUserId(userId)
                            .doOnNext(order -> log.info("Fetched order: {}", order))
                            .map(order -> (Object) order) // Преобразуем `Order` в общий тип
                            .startWith((Object) user) // Добавляем информацию о пользователе как первый элемент
                            .doOnError(error -> log.error("Error fetching orders: ", error));
                });
    }

}

