package org.example.users.repository;

import org.example.users.entity.UserProfile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserProfileRepository extends ReactiveMongoRepository<UserProfile, String> {
    Flux<UserProfile> findByUserId(Long userId);
}

