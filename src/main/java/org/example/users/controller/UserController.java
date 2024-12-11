package org.example.users.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.users.dto.User;
import org.example.users.dto.UserWithOrders;
import org.example.users.entity.UserProfile;
import org.example.users.service.UserProfileService;
import org.example.users.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserProfileService userProfileService;

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build());
    }

    @GetMapping("/{id}")
    public Mono<User> getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/profiles")
    public Flux<UserProfile> getUserProfiles(@PathVariable Long id) {
        return userProfileService.getUserProfiles(id);
    }

    @GetMapping("/cache/{id}")
    public Mono<User> getUserWithCache(@PathVariable Long id) {
        return userService.getUserWithCache(id);
    }

    @PostMapping("/profiles")
    public Mono<UserProfile> saveUserProfile(@RequestBody UserProfile userProfile) {
        return userProfileService.saveUserProfile(userProfile);
    }

    @GetMapping("/{userId}/orders")
    public Flux<Object> getUserWithOrders(@PathVariable Long userId) {
        log.info("getUserWithOrders userId = {}", userId);
        return userService.getUserWithOrders(userId);
    }
}
