package org.example.users.service;

import lombok.RequiredArgsConstructor;
import org.example.users.entity.UserProfile;
import org.example.users.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public Flux<UserProfile> getUserProfiles(Long userId) {
        return userProfileRepository.findByUserId(userId);
    }

    public Mono<UserProfile> saveUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }
}

