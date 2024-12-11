package org.example.users.service;

import lombok.RequiredArgsConstructor;
import org.example.users.dto.User;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserCacheService {
    private final ReactiveRedisTemplate<String, User> redisTemplate;

    public Mono<User> getUserFromCache(Long id) {
        return redisTemplate.opsForValue().get("user:" + id);
    }

    public Mono<Void> saveUserToCache(User user) {
        return redisTemplate.opsForValue().set("user:" + user.getId(), user)
                .then();
    }
}

